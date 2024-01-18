package org.british_information_technologies.gym_log_book.lib.form.input_types

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

class TextField(
	initialValue: String = "",
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
		capitalization = KeyboardCapitalization.Sentences,
		autoCorrect = true,
		KeyboardType.Text,
		imeAction = ImeAction.Next,
	),
	onChange: (String?) -> Unit,
) : TextInput<String, Nothing>(
	initialValue = initialValue,
	keyboard = keyboardOptions,
	onChange = onChange,
	onError = { "<ERRORS NOT POSSIBLE>" },
	validatorFn = { Result.success("") }
)
