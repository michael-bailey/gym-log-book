@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.shared.exercise.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.annotations.Rpc
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Rpc
interface ExerciseTypeController {

	fun exerciseTypes(): Flow<Collection<ExerciseType>>

	suspend fun createExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	): ExerciseType

	suspend fun deleteExerciseTypes(ids: Collection<Uuid>)

}
