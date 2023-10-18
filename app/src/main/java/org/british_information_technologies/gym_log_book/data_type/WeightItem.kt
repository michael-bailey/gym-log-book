package org.british_information_technologies.gym_log_book.data_type

import org.british_information_technologies.gym_log_book.lib.Identifiable
import java.time.LocalDate

data class WeightItem(
	override val id: Int,
	val date: LocalDate,
	val weight: Double,
) : Identifiable()