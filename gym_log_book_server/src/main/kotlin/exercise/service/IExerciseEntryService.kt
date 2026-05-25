@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseEntryService {
	val exerciseEntries: Flow<Collection<ExerciseEntryView>>

	@Deprecated("Use `newEntry`.")
	suspend fun createExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int
	): ExerciseEntry

	suspend fun newEntry(command: NewExerciseEntryCommand): ExerciseEntry
}
