package org.british_information_technologies.gym_log_book.lib.form

import android.util.Log
import androidx.compose.runtime.State
import org.british_information_technologies.gym_log_book.lib.form.input_types.DoubleField
import org.british_information_technologies.gym_log_book.lib.form.input_types.FloatField
import org.british_information_technologies.gym_log_book.lib.form.input_types.FormComponent
import org.british_information_technologies.gym_log_book.lib.form.input_types.PasswordField
import org.british_information_technologies.gym_log_book.lib.form.input_types.TextField

class FormScope {

	var formInputs = mutableListOf<FormComponent>()

	inline fun <reified S : Any> ValueInput(
		state: State<S>,
		noinline onChange: (S) -> Unit
	) {
		when (S::class) {
			String::class -> {
				formInputs.add(
					TextField(
						state as State<String>,
						onChange as (String) -> Unit
					)
				)
			}

			Float::class -> {
				formInputs.add(FloatField(onChange))
			}

			Double::class -> {
				formInputs.add(DoubleField(state as State<Double>, onChange))
			}

			else -> {
				Log.d("FormScope", "TextInput: state isn't an accepted type")
			}
		}
	}

	fun PasswordInput(state: State<String>) {
		formInputs.add(PasswordField(state))
	}

	fun getInputs(): List<FormComponent> = formInputs
}