@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseEntryRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryService(
	private val entryRepository: ExerciseEntryRepository,
) {
	suspend fun createNewExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int,
	) {
		entryRepository.createNewExerciseEntry(
			exerciseTypeId = exerciseTypeId,
			entrySetNumber = entrySetNumber,
			entryWeight = entryWeight,
			entryReps = entryReps,
		)
	}

	val allEntries = entryRepository.allExerciseEntries

}
