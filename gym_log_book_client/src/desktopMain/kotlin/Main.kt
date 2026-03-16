package net.michael_bailey.gym_log_book.desktop

import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main(args: Array<String>) = application {
	Window(onCloseRequest = ::exitApplication, title = "Gym Log Book") {
		Text("Hello world")
	}
}