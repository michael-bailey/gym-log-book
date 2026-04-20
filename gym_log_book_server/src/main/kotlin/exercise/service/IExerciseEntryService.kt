@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseEntryService {
	val exerciseEntries: Flow<Collection<ExerciseEntry>>

	suspend fun createExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int
	): ExerciseEntry
}
