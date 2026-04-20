@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.service

import kotlinx.coroutines.supervisorScope
import net.michael_bailey.gym_log_book.server.exercise.repository.IExerciseEntryRepository
import net.michael_bailey.gym_log_book.server.exercise.repository.IExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory(binds = [IExerciseEntryService::class])
class ExerciseEntryService(
	private val exerciseEntryRepository: IExerciseEntryRepository,
	private val exerciseTypeRepository: IExerciseTypeRepository,
) : IExerciseEntryService {

	override val exerciseEntries = exerciseEntryRepository.exerciseEntries

	override suspend fun createExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int
	): ExerciseEntry = supervisorScope {
		exerciseEntryRepository.createNewEntry(
			exerciseTypeId = exerciseTypeId,
			entrySetNumber = entrySetNumber,
			entryWeight = entryWeight,
			entryReps = entryReps
		).await()
	}


}
