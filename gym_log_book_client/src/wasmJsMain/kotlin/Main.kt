package net.michael_bailey.gym_log_book.wasm

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	ComposeViewport(document.body!!) {

		val (value, setValue) = remember { mutableStateOf("Hello world") }

		Column {
			Text("$value")

			Button(onClick = { setValue("Clicked!") }) {
				Text("Click Me!")
			}
		}
	}
}