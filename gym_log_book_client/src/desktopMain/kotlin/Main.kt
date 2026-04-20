package net.michael_bailey.gym_log_book.client

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.engine.cio.*
import net.michael_bailey.gym_log_book.client.counter.counterClientModule
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.client.counter.view.CounterApp
import net.michael_bailey.gym_log_book.client.platform.platformModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf

fun main() = application {
	val koin = startKoin {
		modules(
			platformModule,
			counterClientModule,
		)
	}.koin

	val counterClientService = koin.get<CounterClientService> {
		parametersOf(CIO.create())
	}

	Window(
		onCloseRequest = {
			stopKoin()
			exitApplication()
		},
		title = "Gym Log Book Counter",
	) {
		CounterApp(counterClientService)
	}
}
