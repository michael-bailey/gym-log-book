package net.michael_bailey.gym_log_book.server.exercise.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import net.michael_bailey.gym_log_book.server.exercise.service.IWeightService
import net.michael_bailey.gym_log_book.shared.exercise.controller.WeightEntryController
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry
import org.koin.core.annotation.Factory

@Factory
class WeightEntryControllerImpl(
	private val weightService: IWeightService,
) : WeightEntryController {
	override fun getWeightEntries(): Flow<Collection<WeightEntry>> =
		weightService.allWeightEntries

	override suspend fun addWeightEntry(
		date: LocalDate,
		weight: Double
	): WeightEntry {
		return weightService.addWeightEntry(date, weight)
	}
}