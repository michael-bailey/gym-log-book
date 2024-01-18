package org.british_information_technologies.gym_log_book.lib.form

import androidx.compose.runtime.Composable

@Composable
fun Form(
	title: String,
	scope: FormScope.() -> Unit
) {

	val scope = FormScope()
	val items = scope.getInputs()

	for (i in items) {
		i.Compose()
	}
}