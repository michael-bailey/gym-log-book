package net.michael_bailey.gym_log_book.shared.exercise.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.rpc.annotations.Rpc
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry

@Rpc
interface WeightEntryController {

	fun getWeightEntries(): Flow<Collection<WeightEntry>>

	suspend fun addWeightEntry(
		date: LocalDate,
		weight: Double
	): WeightEntry

}