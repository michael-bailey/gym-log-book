package net.michael_bailey.gym_log_book.client

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import net.michael_bailey.gym_log_book.client.counter.counterClientModule
import net.michael_bailey.gym_log_book.client.counter.view.CounterScreen
import net.michael_bailey.gym_log_book.client.exercise.exerciseClientModule
import net.michael_bailey.gym_log_book.client.platform.platformModule
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	val koin = startKoin {
		modules(
			platformModule,
			applicationModule,
			counterClientModule,
			exerciseClientModule,
		)
	}.koin

	ComposeViewport(document.body!!) {
		CounterScreen()
	}
}
