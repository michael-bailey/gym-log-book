package io.github.michael_bailey.gym_log_book.lib

import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.lib.exceptions.*
import kotlin.math.sign


object ExerciseVerficationUtils {

	fun verifyId(input: String, items: List<ExerciseItem>): Result<Int> =
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

	fun verifyExerciseName(input: String): Result<String> = kotlin.runCatching {
		if (input == "") {
			throw EmptyInputException()
		}
		input
	}

	fun verifySet(input: String): Result<UInt> = kotlin.runCatching {
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

	fun verifyWeight(input: String): Result<Float> = kotlin.runCatching {
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

	fun verifyReps(input: String): Result<UInt> = kotlin.runCatching {
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
}