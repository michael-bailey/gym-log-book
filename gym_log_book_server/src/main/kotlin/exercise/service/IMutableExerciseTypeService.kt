@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.service

import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IMutableExerciseTypeService : IExerciseTypeService {

	suspend fun createNewExerciseType(name: String, equipmentClass: EquipmentClass): ExerciseType
	suspend fun deleteExerciseTypes(ids: Collection<Uuid>)
}
