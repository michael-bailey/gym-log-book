@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import kotlin.time.Clock
import net.michael_bailey.gym_log_book.client.exercise.factory.IExerciseEntryEntityFactory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryRepository(
	private val controller: Flow<ExerciseEntryController?>,
	private val exerciseEntryEntityFactory: IExerciseEntryEntityFactory,
	private val dao: ExerciseEntryDao,
	private val clock: Clock,
	private val coroutineScope: CoroutineScope,
) {

	private val _exerciseEntryController = controller.stateIn(coroutineScope, SharingStarted.Eagerly, initialValue = null)

	val allExerciseEntries =
		controller.flatMapLatest { it?.getExerciseEntries() ?: flowOf(emptyList()) }.catch { emit(emptyList()) }

	suspend fun createNewExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int,
	) {

		_exerciseEntryController.value?.newEntry(
			NewExerciseEntryCommand(
				exerciseTypeId = exerciseTypeId,
				creationInstant = clock.now(),
				setNumber = entrySetNumber,
				weight = entryWeight,
				reps = entryReps,
			)
		)
		val entry = exerciseEntryEntityFactory.create(
			exerciseTypeId = exerciseTypeId,
			setNumber = entrySetNumber,
			weight = entryWeight,
			reps = entryReps,
		)

		dao.createExerciseEntry(entry)
	}
}