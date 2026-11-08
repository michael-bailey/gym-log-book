@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseEntryRepository {
	suspend fun createNewExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int,
	)
}