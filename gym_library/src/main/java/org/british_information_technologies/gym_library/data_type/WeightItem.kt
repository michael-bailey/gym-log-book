package org.british_information_technologies.gym_library.data_type

import java.time.LocalDate

data class WeightItem(
	override val id: Int,
	val date: LocalDate,
	val weight: Double,
) : Identifiable()