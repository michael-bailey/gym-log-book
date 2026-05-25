@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.controller

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.server.exercise.service.IExerciseEntryService
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory(binds = [ExerciseEntryController::class])
class ExerciseEntryControllerImpl(
	private val exerciseEntryService: IExerciseEntryService,
) : ExerciseEntryController {

	override fun getExerciseEntries(): Flow<Collection<ExerciseEntryView>> = exerciseEntryService.exerciseEntries

	@Deprecated("Use `newEntry`.")
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

	override suspend fun newEntry(command: NewExerciseEntryCommand): ExerciseEntry =
		exerciseEntryService.newEntry(command)

}
