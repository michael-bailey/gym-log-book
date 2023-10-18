package org.british_information_technologies.gym_log_book.lib.validation

import org.british_information_technologies.gym_log_book.data_type.ExerciseItem
import org.british_information_technologies.gym_log_book.lib.exceptions.DecimalPointException
import org.british_information_technologies.gym_log_book.lib.exceptions.EmptyInputException
import org.british_information_technologies.gym_log_book.lib.exceptions.ItemExistsException
import org.british_information_technologies.gym_log_book.lib.exceptions.MultipleDecimalPointException
import org.british_information_technologies.gym_log_book.lib.exceptions.NegativeNumberException
import kotlin.math.sign

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