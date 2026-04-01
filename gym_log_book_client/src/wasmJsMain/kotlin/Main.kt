package net.michael_bailey.gym_log_book.client

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import net.michael_bailey.gym_log_book.client.counter.counterClientModule
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.client.counter.view.CounterApp
import net.michael_bailey.gym_log_book.client.platform.platformModule
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	val koin = startKoin {
		modules(
			platformModule,
			counterClientModule
		)
	}.koin

	val counterClientService = koin.get<CounterClientService>()

	ComposeViewport(document.body!!) {
		CounterApp(counterClientService)
	}
}
