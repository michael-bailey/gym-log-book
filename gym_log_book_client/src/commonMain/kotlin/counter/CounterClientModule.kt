package net.michael_bailey.gym_log_book.client.counter

import io.ktor.client.*
import io.ktor.client.engine.*
import kotlinx.rpc.RpcClient
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.config.CounterClientConfig
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import org.koin.dsl.bind
import org.koin.dsl.module

val counterClientModule = module {
	single {
		HttpClient(get<HttpClientEngine>()) {
			installKrpc()
		}
	}

	single {
		get<HttpClient>().rpc(CounterClientConfig.counterRpcUrl) {
			rpcConfig {
				serialization {
					json()
				}
			}
		}
	} bind RpcClient::class

	single {
		get<RpcClient>().withService<CounterController>()
	} bind CounterController::class

	single {

		val controller = get<CounterController>()

		CounterClientService(
			counterController = controller
		)
	}
}

