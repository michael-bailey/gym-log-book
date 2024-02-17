package org.british_information_technologies.gym_log_book.lib.form.input_types

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

abstract class FormComponent {
	@Composable
	open fun Compose() {
		Text(text = "This needs to be overridden")
	}
}