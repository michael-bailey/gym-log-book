package org.british_information_technologies.gym_library.data_type

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
