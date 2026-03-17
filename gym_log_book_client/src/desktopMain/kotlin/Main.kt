package net.michael_bailey.gym_log_book.client

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.client.config.COUNTER_RPC_URL
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.client.counter.view.CounterApp
import net.michael_bailey.gym_log_book.client.di.counterClientModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun main() = application {
	val httpClient = HttpClient(CIO) {
		installKrpc()
	}
	val rpcClient = httpClient.rpc(COUNTER_RPC_URL) {
		rpcConfig {
			serialization {
				json()
			}
		}
	}
	val koin = startKoin {
		modules(counterClientModule(rpcClient))
	}.koin
	val counterClientService = koin.get<CounterClientService>()

	Window(
		onCloseRequest = {
			httpClient.close()
			stopKoin()
			exitApplication()
		},
		title = "Gym Log Book Counter",
	) {
		CounterApp(counterClientService)
	}
}
