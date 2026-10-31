@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package net.michael_bailey.gym_log_book.client.application

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import net.michael_bailey.gym_log_book.shared.application.HeartbeatController
import kotlin.time.Duration.Companion.seconds

class Heartbeat(
	private val heartbeatController: Flow<HeartbeatController?>,
	private val coroutineScope: CoroutineScope,
) {

	private val _heartbeatJob: Job
	private val _status = MutableStateFlow(Status.Restarting)

	val status: Flow<Status> = _status

	init {
		_heartbeatJob = coroutineScope.launch { runHeartbeat() }
	}

	private suspend fun runHeartbeat() {
		heartbeatController
			.flatMapLatest { controller ->
				controller?.let { getHeartbeat(it) }?.onStart {
					println("[Heartbeat] new controller, resetting to Restarting")
					emit(Status.Restarting)
				} ?: flowOf(Status.Dead)
			}
			.collect { _status.value = it }
	}

	private fun getHeartbeat(controller: HeartbeatController): Flow<Status> = controller.heartbeat()
		.timeout(2.seconds)
		.map { Status.Alive }
		.retry(retries = 5) { e ->
			if (e is TimeoutCancellationException) {
				_status.emit(Status.Restarting)
				delay(1.seconds)
				true
			} else false
		}
		.catch { e ->
			println("[Heartbeat] giving up: $e")
			emit(Status.Dead)
		}

	enum class Status {
		Alive,
		Restarting,
		Dead,
	}
}