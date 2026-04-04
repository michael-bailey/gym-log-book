@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.controller

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.server.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.server.exercise.service.IExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory
class ExerciseEntryControllerImpl(
	private val exerciseEntryService: ExerciseEntryService,
	private val exerciseTypeService: IExerciseTypeService,
) : ExerciseEntryController {

	override fun getExerciseEntries(): Flow<Collection<ExerciseEntry>> = exerciseEntryService.exerciseEntries

	override suspend fun createEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int
	) = exerciseEntryService.createExerciseEntry(
		exerciseTypeId = exerciseTypeId,
		entrySetNumber = entrySetNumber,
		entryWeight = entryWeight,
		entryReps = entryReps
	)

}