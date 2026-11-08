package net.michael_bailey.gym_log_book.client.exercise.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView

interface INetworkExerciseEntryService : IExerciseEntryService {
	val allEntries: Flow<Collection<ExerciseEntryView>>
}