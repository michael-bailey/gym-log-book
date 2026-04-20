package net.michael_bailey.gym_log_book.server.exercise.service

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import net.michael_bailey.gym_log_book.server.exercise.repository.IMutableWeightEntryRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry
import org.koin.core.annotation.Factory

@Factory
class WeightService(
	private val weightEntryRepository: IMutableWeightEntryRepository
) : IWeightService {

	override val allWeightEntries: Flow<Collection<WeightEntry>> = weightEntryRepository.allWeightEntries

	override suspend fun addWeightEntry(date: LocalDate, weight: Double): WeightEntry =
		weightEntryRepository.addWeightEntry(date, weight).await()

}