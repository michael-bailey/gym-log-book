package org.british_information_technologies.gym_log_book.data_type

import org.british_information_technologies.gym_log_book.lib.Identifiable

data class ExerciseType(
	override val id: Int,
	val name: String,
	val isUsingUserWeight: Boolean,
) : Identifiable()