package org.british_information_technologies.gym_log_book.lib.validation

import org.british_information_technologies.gym_library.data_type.ExerciseItem
import org.british_information_technologies.gym_log_book.lib.exceptions.DecimalPointException
import org.british_information_technologies.gym_log_book.lib.exceptions.EmptyInputException
import org.british_information_technologies.gym_log_book.lib.exceptions.MultipleDecimalPointException
import org.british_information_technologies.gym_log_book.lib.exceptions.NegativeNumberException
import org.british_information_technologies.gym_log_book.lib.exceptions.UUIDInvalidException
import java.util.UUID
import kotlin.math.sign

object FormValidationFunctions {

	fun verifyUUID(input: String, items: List<ExerciseItem>): Result<UUID> =
		kotlin.runCatching {

			if (input == "") {
				throw EmptyInputException()
			}

			val uuidRegex =
				Regex("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$")

			val regexMatches = uuidRegex.matches(input)

			if (regexMatches) {
				UUID.fromString(input)
			} else {
				throw UUIDInvalidException()
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