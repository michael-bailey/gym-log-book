package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry

interface IWeightEntryRepository {

	val allWeightEntries: Flow<Collection<WeightEntry>>

}