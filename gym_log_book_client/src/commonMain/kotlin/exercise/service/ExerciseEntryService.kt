@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryService(
	private val exerciseEntryController: ExerciseEntryController
) {
	suspend fun createNewExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int,
	) {
		exerciseEntryController.createEntry(
			exerciseTypeId = exerciseTypeId,
			entrySetNumber = entrySetNumber,
			entryWeight = entryWeight,
			entryReps = entryReps,
		)
	}

	val allEntries = exerciseEntryController.getExerciseEntries()

}