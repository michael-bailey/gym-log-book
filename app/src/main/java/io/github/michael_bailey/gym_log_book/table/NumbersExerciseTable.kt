package io.github.michael_bailey.gym_log_book.table

import android.content.Context
import io.github.michael_bailey.gym_log_book.data_type.NumbersExerciseItem
import io.github.michael_bailey.gym_log_book.lib.table.CSVTable
import java.time.LocalDate

class NumbersExerciseTable(
	private val context: Context,
	override val tableName: String
) : CSVTable<NumbersExerciseItem>(context) {

	private var currentID = 0

	override fun formatHeader(): String {
		return "Date, Exercise, Set number, Weight, Reps"
	}

	override fun decodeEntry(entry: String): NumbersExerciseItem {
		val (
			date,
			exercise,
			set,
			weight,
			reps
		) = entry.split(
			", ",
			ignoreCase = false,
			limit = 5
		)

		return NumbersExerciseItem(
			exercise = exercise,
			setNumber = set.toInt(),
			date = LocalDate.parse(date),
			reps = reps.toInt(),
			weight = weight.toDouble(),
			id = currentID++
		)
	}

	override fun formatEntry(entry: NumbersExerciseItem): String {
		return "${entry.date}, ${entry.exercise}, ${entry.setNumber}, ${entry.weight}, ${entry.reps}"
	}
}