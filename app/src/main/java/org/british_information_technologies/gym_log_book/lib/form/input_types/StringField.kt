package org.british_information_technologies.gym_log_book.lib.form.input_types

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import org.british_information_technologies.gym_log_book.lib.form.FormState

class StringField(
	name: String,
	formState: FormState,
) : FormComponent<String>(
	name = name,
	formState = formState,
) {

	@Composable
	override fun Compose() {
		val state by getState("")
		OutlinedTextField(value = state, onValueChange = this::setState)
	}

}
