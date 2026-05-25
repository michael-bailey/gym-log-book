@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import net.michael_bailey.gym_log_book.server.exercise.mapper.toDomain
import net.michael_bailey.gym_log_book.server.exercise.mapper.toShared
import net.michael_bailey.gym_log_book.server.exercise.mapper.toView
import net.michael_bailey.gym_log_book.server.exercise.repository.IExerciseEntryRepository
import net.michael_bailey.gym_log_book.server.exercise.repository.IExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import org.koin.core.annotation.Factory
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory(binds = [IExerciseEntryService::class])
class ExerciseEntryService(
	private val exerciseEntryRepository: IExerciseEntryRepository,
	private val exerciseTypeRepository: IExerciseTypeRepository,
	private val clock: Clock,
) : IExerciseEntryService {

	override val exerciseEntries: Flow<Collection<ExerciseEntryView>> = combine(
		exerciseEntryRepository.exerciseEntries,
		exerciseTypeRepository.allExerciseTypes,
	) { exerciseEntries, exerciseTypes ->
		val exerciseTypeNames = exerciseTypes.associate { it.id to it.name }
		exerciseEntries.map { exerciseEntry ->
			exerciseEntry.toView(
				exerciseTypeName = exerciseTypeNames[exerciseEntry.exerciseTypeId] ?: UNKNOWN_EXERCISE_TYPE_NAME,
			)
		}
	}

	@Deprecated("Use `newEntry`.")
	override suspend fun createExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int
	): ExerciseEntry = newEntry(
		command = NewExerciseEntryCommand(
			exerciseTypeId = exerciseTypeId,
			creationInstant = clock.now(),
			setNumber = entrySetNumber,
			weight = entryWeight,
			reps = entryReps,
		)
	)

	override suspend fun newEntry(command: NewExerciseEntryCommand): ExerciseEntry {
		exerciseTypeRepository.getExerciseType(command.exerciseTypeId)
			?: throw NoSuchElementException("Exercise type ${command.exerciseTypeId} was not found.")

		val exerciseEntry = exerciseEntryRepository.createEntry(command.toDomain())

		return exerciseEntry.toShared()
	}

	private companion object {
		private const val UNKNOWN_EXERCISE_TYPE_NAME = "Unknown Exercise Type"
	}
}
