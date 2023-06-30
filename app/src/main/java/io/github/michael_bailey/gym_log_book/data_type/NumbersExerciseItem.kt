package io.github.michael_bailey.gym_log_book.data_type

import io.github.michael_bailey.gym_log_book.lib.Identifiable
import java.time.LocalDate

data class NumbersExerciseItem(
	val exercise: String,
	val date: LocalDate,
	var setNumber: Int,
	var weight: Double,
	var reps: Int,
	override var id: Int
) : Identifiable()