package io.github.michael_bailey.gym_log_book.lib

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.lib.exceptions.*
import kotlin.math.sign

sealed class Validator(
	open val validator: (String) -> Result<Unit>,
	open val keyboardOptions: KeyboardOptions,
) {
	object NumberValidator : Validator(
		ExerciseVerficationUtils::verifyNumber,
		KeyboardOptions(
			autoCorrect = false,
			keyboardType = KeyboardType.Number,
			capitalization = KeyboardCapitalization.None,
			imeAction = ImeAction.Next
		)
	)

	object StringNameValidator : Validator(
		ExerciseVerficationUtils::verifyString,
		KeyboardOptions(
			autoCorrect = false,
			keyboardType = KeyboardType.Text,
			capitalization = KeyboardCapitalization.Words,
			imeAction = ImeAction.Next,
		)
	)

	object FloatValidator : Validator(
		ExerciseVerficationUtils::verifyFloat,
		KeyboardOptions(
			autoCorrect = false,
			keyboardType = KeyboardType.Number,
			capitalization = KeyboardCapitalization.None,
			imeAction = ImeAction.Next,
		)
	)
}

object ExerciseVerficationUtils {

	fun verifyId(input: String, items: List<ExerciseItem>): Result<Unit> =
		kotlin.runCatching {
			if (input == "") {
				throw EmptyInputException()
			}
			if (input.find { it == '-' } != null) {
				throw NegativeNumberException()
			}
			if (input.find { it == '.' } != null) {
				throw DecimalPointException()
			}
			val id = input.toInt()
			if (id !in items.map { it.id }) {
				id
			} else {
				throw ItemExistsException(id)
			}
		}

	fun verifyString(input: String): Result<Unit> = kotlin.runCatching {
		if (input == "") {
			throw EmptyInputException()
		}
		input
	}

	fun verifyNumber(input: String): Result<Unit> = kotlin.runCatching {
		if (input == "") {
			throw EmptyInputException()
		}
		if (input.find { it == '-' } != null) {
			throw NegativeNumberException()
		}
		if (input.find { it == '.' } != null) {
			throw DecimalPointException()
		}
		input.toUInt()
	}

	fun verifyFloat(input: String): Result<Unit> = kotlin.runCatching {
		if (input == "") {
			throw EmptyInputException()
		}
		if (input.find { it == '-' } != null) {
			throw NegativeNumberException()
		}
		if (input.count { it == '.' } > 1) {
			throw MultipleDecimalPointException()
		}
		input.toFloat().also {
			if (it.sign < 1) {
				throw java.lang.NumberFormatException("Cannot be Negative or Zero")
			}
		}
	}
}