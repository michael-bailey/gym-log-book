package org.british_information_technologies.gym_library.data_type

import java.time.LocalDate

data class NumbersExerciseItem(
	val exercise: String,
	val date: LocalDate,
	var setNumber: Int,
	var weight: Double,
	var reps: Int,
	override var id: Int
) : Identifiable()