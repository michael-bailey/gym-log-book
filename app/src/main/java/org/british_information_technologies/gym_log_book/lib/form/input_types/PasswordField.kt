package org.british_information_technologies.gym_log_book.lib.form.input_types

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.british_information_technologies.gym_log_book.lib.form.FormState

class PasswordField(
	name: String,
	formState: FormState,
	val label: String?
) : FormComponent<String>(
	name = name,
	formState = formState,
) {

	@Composable
	override fun Compose() {
		val state by getState("")

		var isVisible by remember { mutableStateOf(false) }

		val icon =
			if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

		OutlinedTextField(
			modifier = Modifier.fillMaxWidth(),
			value = state,
			onValueChange = this::setState,
			label = label?.let { { Text(text = it) } },
			visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
			trailingIcon = {
				IconButton(onClick = { isVisible = !isVisible }) {
					Icon(
						imageVector = icon,
						contentDescription = "Password visibility toggle"
					)
				}
			}
		)
	}

}
