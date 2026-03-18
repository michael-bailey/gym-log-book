package net.michael_bailey.gym_log_book

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.ktor.client.*
import io.ktor.client.engine.js.*
import kotlinx.browser.document
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.client.config.CounterClientConfig
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.client.counter.view.CounterApp
import net.michael_bailey.gym_log_book.client.di.counterClientModule
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	val httpClient = HttpClient(Js) {
		installKrpc()
	}
	val rpcClient = httpClient.rpc(CounterClientConfig.counterRpcUrl) {
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

	ComposeViewport(document.body!!) {
		CounterApp(counterClientService)
	}
}
