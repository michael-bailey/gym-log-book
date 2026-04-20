@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.controller

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.server.exercise.service.IMutableExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory(binds = [ExerciseTypeController::class])
class ExerciseTypeControllerImpl(
	private val exerciseTypeService: IMutableExerciseTypeService
) : ExerciseTypeController {

	override fun exerciseTypes(): Flow<Collection<ExerciseType>> = exerciseTypeService.exerciseTypes

	override suspend fun createExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	): ExerciseType {
		return exerciseTypeService.createNewExerciseType(
			name = name,
			equipmentClass = equipmentClass,
		)
	}

	override suspend fun deleteExerciseTypes(ids: Collection<Uuid>) {
		exerciseTypeService.deleteExerciseTypes(ids)
	}
}
