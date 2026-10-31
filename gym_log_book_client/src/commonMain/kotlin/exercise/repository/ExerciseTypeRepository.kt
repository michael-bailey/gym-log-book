@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseTypeCommand
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseTypeRepository(
	private val controller: Flow<ExerciseTypeController?>,
	private val coroutineScope: CoroutineScope,
) {

	private val _exerciseTypeController = controller
		.stateIn(coroutineScope, SharingStarted.Eagerly, initialValue = null)

	val allExerciseTypes = controller
		.flatMapLatest { it?.exerciseTypes() ?: flowOf(emptyList()) }
		.catch { emit(emptyList()) }

	suspend fun createNewExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	) {
		_exerciseTypeController.value?.newType(
			NewExerciseTypeCommand(
				name = name,
				equipmentClass = equipmentClass
			)
		)
	}

	suspend fun deleteExerciseTypes(ids: Collection<Uuid>) {
		_exerciseTypeController.value?.deleteExerciseTypes(
			ids
		)
	}

}