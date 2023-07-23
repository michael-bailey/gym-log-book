package io.github.michael_bailey.gym_log_book.lib.validation

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

sealed class Validator(
	isLast: Boolean,
	open val validator: (String) -> Result<Unit>
) {

	open var keyboard = KeyboardOptions(
		autoCorrect = false,
		keyboardType = KeyboardType.Text,
		capitalization = KeyboardCapitalization.Words,
	)

	init {
		keyboard = keyboard.copy(
			imeAction = if (isLast) ImeAction.Done else ImeAction.Next
		)
	}

	class NumberValidator(isLast: Boolean = false) : Validator(
		isLast,
		ExerciseVerficationUtils::verifyNumber,
	)

	class StringNameValidator(isLast: Boolean = false) : Validator(
		isLast,
		ExerciseVerficationUtils::verifyString,
	)

	class FloatValidator(isLast: Boolean = false) : Validator(
		isLast,
		ExerciseVerficationUtils::verifyFloat,
	)
}