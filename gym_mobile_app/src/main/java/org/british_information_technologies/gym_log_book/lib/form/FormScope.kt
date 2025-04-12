package org.british_information_technologies.gym_log_book.lib.form

import org.british_information_technologies.gym_log_book.lib.form.input_types.FormComponent
import org.british_information_technologies.gym_log_book.lib.form.input_types.PasswordField

class FormScope(
	private val formState: FormState,
) {

	private var formInputs = mutableListOf<FormComponent<*>>()

	fun string() {
	}

	fun password(
		name: String,
		label: String? = null
	) {
		formInputs += PasswordField(
			name = name,
			formState = formState,
			label = label,
		)
	}

	fun getComponents(): List<FormComponent<*>> {
		return formInputs
	}
}