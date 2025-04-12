package org.british_information_technologies.gym_library.data_type

data class ExerciseType(
	override val id: Int,
	val name: String,
	val isUsingUserWeight: Boolean,
) : Identifiable()