package io.github.michael_bailey.gym_log_book.data_type

import io.github.michael_bailey.gym_log_book.lib.Identifiable
import java.time.LocalDate

/// This represents an item in the exercise list
data class ExerciseItem(
	override val id: Long,
	val date: LocalDate,
	var exercise: String,
	var setNumber: Int,
	var weight: Int,
	var reps: Int,
) : Identifiable()
