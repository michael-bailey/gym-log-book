package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.Deferred
import kotlinx.datetime.LocalDate
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry

interface IMutableWeightEntryRepository : IWeightEntryRepository {

	fun addWeightEntry(
		date: LocalDate,
		weight: Double,
	): Deferred<WeightEntry>

}