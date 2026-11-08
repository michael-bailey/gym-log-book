package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView

interface IExerciseEntryNetworkRepository : IExerciseEntryRepository {
	val allExerciseEntries: Flow<Collection<ExerciseEntryView>>
}