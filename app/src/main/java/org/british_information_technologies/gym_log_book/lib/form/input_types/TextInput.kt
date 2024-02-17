package org.british_information_technologies.gym_log_book.lib.form.input_types

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation

abstract class TextInput<T, E : Throwable>(
	private val initialValue: String = "",
	private val keyboard: KeyboardOptions = KeyboardOptions.Default,
	private val isLastAction: Boolean = false,
	private val transformer: VisualTransformation = VisualTransformation.None,
	private val validatorFn: (String) -> Result<T>,
	private val onError: (E) -> String,
	private val onChange: (T?) -> Unit
) : FormComponent() {

	@Composable
	override fun Compose() {

		var state by remember { mutableStateOf(initialValue) }
		var errorMessage by remember { mutableStateOf<String?>(null) }

		LaunchedEffect(state) {
			val validatorResult = validatorFn(state)
			if (validatorResult.isSuccess) {
				errorMessage = null
				onChange(validatorResult.getOrNull())
			} else {
				errorMessage = onError(validatorResult.exceptionOrNull() as E)
				onChange(validatorResult.getOrNull())
			}
		}

		OutlinedTextField(
			value = state,
			onValueChange = {
				state = it
			},
			keyboardOptions = keyboard.copy(
				imeAction = if (isLastAction) {
					ImeAction.Done
				} else {
					ImeAction.Next
				}
			)
		)
	}
}