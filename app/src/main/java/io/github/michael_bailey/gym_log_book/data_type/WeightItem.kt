package io.github.michael_bailey.gym_log_book.data_type

import io.github.michael_bailey.gym_log_book.lib.Identifiable
import java.time.LocalDate

data class WeightItem(
	override val id: Int,
	val date: LocalDate,
	val weight: Double,
) : Identifiable()