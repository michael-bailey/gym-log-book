package org.british_information_technologies.gym_log_book.lib.form.input_types

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import org.british_information_technologies.gym_log_book.lib.form.FormState

abstract class FormComponent<S : Any>(
	protected val name: String,
	protected val formState: FormState,
) {

	fun getState(default: S): State<S> {
		return formState.getState(name, default)
	}

	fun setState(value: S) {
		formState.setState(name, value)
	}


	@Composable
	abstract fun Compose()
}