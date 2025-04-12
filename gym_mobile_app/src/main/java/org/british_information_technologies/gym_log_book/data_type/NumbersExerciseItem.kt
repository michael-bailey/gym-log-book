package org.british_information_technologies.gym_log_book.data_type

import org.british_information_technologies.gym_log_book.lib.Identifiable
import java.time.LocalDate

data class NumbersExerciseItem(
	val exercise: String,
	val date: LocalDate,
	var setNumber: Int,
	var weight: Double,
	var reps: Int,
	override var id: Int
) : Identifiable()