package net.michael_bailey.gym_log_book.client.window.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import net.michael_bailey.gym_log_book.client.authentication.view.LoginPage

@Composable
fun ExerciseHomeWindow(
//	windowViewModel: ExerciseHomeWindowViewModel = koinViewModel()
) {
	Window(
		onCloseRequest = {},
	) {
		LoginPage()
	}
}