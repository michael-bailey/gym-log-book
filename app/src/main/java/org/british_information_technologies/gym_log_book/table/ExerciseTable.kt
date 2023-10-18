package org.british_information_technologies.gym_log_book.table

import android.content.Context
import org.british_information_technologies.gym_log_book.data_type.ExerciseItem
import org.british_information_technologies.gym_log_book.lib.table.CSVTable
import java.time.LocalDate

class ExerciseTable(val context: Context) : CSVTable<ExerciseItem>(context) {

	override val tableName: String
		get() = "exercise"

	override fun formatHeader(): String =
		"'ID', 'Date', 'Exercise', 'SetNumber', 'Weight', 'Reps'"

	override fun decodeEntry(entry: String): ExerciseItem {
		val (id, date, exercise, setNumber, Weight, Reps) = entry.split(
			", ",
			ignoreCase = false,
			limit = 6
		)
		return ExerciseItem(
			id.toInt(),
			LocalDate.parse(date),
			exercise,
			setNumber.toInt(),
			Weight.toDouble(),
			Reps.toInt()
		)
	}

	override fun formatEntry(entry: ExerciseItem): String {
		return entry.let { "${it.id}, ${it.date}, ${it.exercise}, ${it.setNumber}, ${it.weight}, ${it.reps}" }
	}
}

private operator fun <E : Any> List<E>.component6() = get(5)
