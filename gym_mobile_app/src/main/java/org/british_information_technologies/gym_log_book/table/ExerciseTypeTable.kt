package org.british_information_technologies.gym_log_book.table

import android.content.Context
import org.british_information_technologies.gym_library.data_type.ExerciseType
import org.british_information_technologies.gym_log_book.lib.table.CSVTable

class ExerciseTypeTable(val context: Context) :
	CSVTable<ExerciseType>(context) {

	override val tableName: String
		get() = "exerciseType"

	override fun formatHeader(): String =
		"'ID', 'Name', 'Is Using User Weight'"

	override fun decodeEntry(entry: String): ExerciseType {
		val (id, name, isUsingUserWeight) = entry.split(
			", ",
			ignoreCase = false,
			limit = 3
		)
		return ExerciseType(
			id.toInt(),
			name,
			isUsingUserWeight.toBoolean()
		)
	}

	override fun formatEntry(entry: ExerciseType): String {
		return entry.let { "${it.id}, ${it.name}, ${it.isUsingUserWeight}" }
	}
}