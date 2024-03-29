package org.british_information_technologies.gym_log_book.data_type

import org.british_information_technologies.gym_log_book.lib.Identifiable
import java.time.LocalDate

/// This represents an item in the exercise list
data class ExerciseItem(
	override val id: Int,
	val date: LocalDate,
	var exercise: String,
	var setNumber: Int,
	var weight: Double,
	var reps: Int,
) : Identifiable()
