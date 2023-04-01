package io.github.michael_bailey.gym_log_book.data_type

import io.github.michael_bailey.gym_log_book.lib.Identifiable

data class ExerciseType(
	override val id: Int,
	val name: String,
) : Identifiable()