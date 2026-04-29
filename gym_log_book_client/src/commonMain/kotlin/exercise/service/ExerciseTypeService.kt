@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseTypeService(
	private val exerciseTypeController: ExerciseTypeController
) {
	suspend fun createNewType(
		name: String,
		equipmentClass: EquipmentClass,
	) {
		exerciseTypeController.createExerciseType(
			name = name,
			equipmentClass = equipmentClass,
		)
	}

	suspend fun deleteTypes(ids: Collection<Uuid>) {
		exerciseTypeController.deleteExerciseTypes(ids)
	}

	val exerciseTypes = exerciseTypeController.exerciseTypes()

}
