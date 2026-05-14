package net.michael_bailey.gym_log_book.client.window.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.home.HomePage
import net.michael_bailey.gym_log_book.client.util.KoinScope
import net.michael_bailey.gym_log_book.client.util.rememberKoinScope

@Composable
fun ExerciseHomeWindow() {

	val scope = rememberKoinScope<AuthenticatedScope>()
	
	KoinScope(scope = scope) {
		Window(
			onCloseRequest = {},
		) {
			HomePage()
		}
	}
}