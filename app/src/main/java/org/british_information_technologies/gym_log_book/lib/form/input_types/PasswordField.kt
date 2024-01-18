package org.british_information_technologies.gym_log_book.lib.form.input_types

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

class PasswordField(
	initialValue: String = "",
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
		capitalization = KeyboardCapitalization.None,
		autoCorrect = false,
		KeyboardType.Password,
		imeAction = ImeAction.Next,
	),
	validation: (String) -> Result<String>,
	onChange: (String?) -> Unit,
) : TextInput<String, Nothing>(
	initialValue = initialValue,
	keyboard = keyboardOptions,
	onChange = onChange,
	onError = { "" },
	validatorFn = validation,
	transformer = PasswordVisualTransformation(),
)
