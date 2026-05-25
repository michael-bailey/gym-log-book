package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel

interface IExerciseEntryRepository {
	val exerciseEntries: Flow<Collection<ExerciseEntryModel>>
	suspend fun createEntry(newExerciseEntry: NewExerciseEntryModel): ExerciseEntryModel
}
