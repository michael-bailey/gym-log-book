@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.factory

import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Single
class InMemoryWeightEntryFactory : IWeightEntryFactory {
	override fun createEntry(
		date: LocalDate,
		weight: Double
	): WeightEntry = WeightEntry(
		id = Uuid.random(),
		date = date.atTime(0, 0),
		weight = weight
	)
}