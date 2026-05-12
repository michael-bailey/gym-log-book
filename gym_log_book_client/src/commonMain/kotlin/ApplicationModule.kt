package net.michael_bailey.gym_log_book.client

import io.ktor.client.*
import io.ktor.client.engine.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.rpc.RpcClient
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.client.config.ClientConfig
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

const val APP_SCOPE_QUALIFIER = "app-scope"

val applicationModule = module {

	single { ClientConfig() }
	single(named(APP_SCOPE_QUALIFIER)) { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

	single {
		println("Creating application level http Client")
		HttpClient(get<HttpClientEngine>()) {
			installKrpc()
		}
	}

	single {
		val clientConfig = get<ClientConfig>()
		val client = get<HttpClient>()
		val rpc = client.rpc(clientConfig.publicUrl.toString()) {
			rpcConfig {
				serialization { json() }
			}
		}
		rpc
	} bind RpcClient::class
}
