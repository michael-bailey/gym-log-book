package net.michael_bailey.gym_log_book.server.exercise.service

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry

interface IWeightService {
	val allWeightEntries: Flow<Collection<WeightEntry>>

	suspend fun addWeightEntry(date: LocalDate, weight: Double): WeightEntry
}