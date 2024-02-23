package org.british_information_technologies.gym_log_book.lib.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Form(
	formState: FormState = FormState(),
	onSubmit: (Map<String, Any>) -> Unit,
	onCancel: (() -> Unit)?,
	scope: FormScope.() -> Unit
) {
	val scope = FormScope(formState = formState)
	val items = scope.getComponents()

	Column(
		modifier = Modifier
			.width(200.dp)
			.height(400.dp)
			.background(Color.Yellow),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		for (i in items) {
			i.Compose()
		}
	}
}