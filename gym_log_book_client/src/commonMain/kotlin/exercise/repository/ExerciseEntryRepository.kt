@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * # ExerciseEntryRepository
 *
 * This class handles the fetching of exercise entry data to the UI.
 *
 * it interfaces with The local room DAO to expose live data
 *
 * ---
 *
 * This is currently being migrated
 * - From sending all data to the server
 * - To storing the data locally, to then be synced by the sync manager
 *
 * during this data will be saved locally, and synced to the server
 */
class ExerciseEntryRepository(
	private val controller: Flow<ExerciseEntryController?>,
	private val clock: Clock,
	private val coroutineScope: CoroutineScope,
) : IExerciseEntryNetworkRepository {

	private val _exerciseEntryController = controller.stateIn(coroutineScope, SharingStarted.Eagerly, initialValue = null)

	override val allExerciseEntries = controller
		.flatMapLatest(::flatMapEntryFlow)
		.catch { emit(emptyList()) }

	override suspend fun createNewExerciseEntry(
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

	}

	private fun flatMapEntryFlow(
		controller: ExerciseEntryController?,
	): Flow<Collection<ExerciseEntryView>> = controller
		?.getExerciseEntries() ?: flowOf(emptyList())

}