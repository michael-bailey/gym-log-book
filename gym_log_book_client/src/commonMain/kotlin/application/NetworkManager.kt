@file:OptIn(ExperimentalCoroutinesApi::class)

package net.michael_bailey.gym_log_book.client.application

import io.ktor.client.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.rpc.RpcClient
import kotlinx.rpc.annotations.Rpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.config.ClientConfig
import net.michael_bailey.gym_log_book.shared.application.HeartbeatController
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform
import kotlin.time.Duration.Companion.seconds

class NetworkManager(
	private val httpClient: HttpClient,
	private val configuration: ClientConfig,
	private val coroutineScope: CoroutineScope,
) {

	private val _rpcClientFlow = MutableStateFlow(createRpc())
	private val resuscitateJob: Job

	@Deprecated("THIS IS FOR INLINE FUNCTION DO NOT USE")
	val rpcClient: Flow<RpcClient> = _rpcClientFlow

	private val heartbeat: Heartbeat = KoinPlatform.getKoin()
		.get {
			println("[NetworkManager::heartbeat] creating heartbeat object")
			parametersOf(getService<HeartbeatController>(), coroutineScope)
		}

	val status: StateFlow<Status> = heartbeat.status
		.map(::mapStatus)
		.stateIn(coroutineScope, SharingStarted.Eagerly, Status.Connecting)

	init {
		resuscitateJob = coroutineScope.launch {
			heartbeat.status
				.filter { it == Heartbeat.Status.Dead }
				.collect { resuscitate() }
		}
	}

	inline fun <@Rpc reified T : Any> getService(): Flow<T> =
		rpcClient.map { it.withService<T>() }

	private fun createRpc(): RpcClient = httpClient.rpc(configuration.publicUrl.toString()) {
		rpcConfig {
			serialization { json() }
		}
	}

	private suspend fun resuscitate() {
		println("[NetworkManager::resuscitate] Resuscitating")
		delay(5.seconds)
		_rpcClientFlow.emit(createRpc())
	}

	private fun mapStatus(
		heartbeatStatus: Heartbeat.Status,
	): Status = when (heartbeatStatus) {
		Heartbeat.Status.Alive -> Status.Connected
		Heartbeat.Status.Restarting -> Status.Connecting
		Heartbeat.Status.Dead -> Status.Disconnected
	}

	enum class Status {
		Connected,
		Connecting,
		Disconnected,
	}
}