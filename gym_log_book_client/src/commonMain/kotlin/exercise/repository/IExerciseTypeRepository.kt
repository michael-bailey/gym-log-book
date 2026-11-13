@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseTypeRepository {
	suspend fun createNewExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	)

	suspend fun deleteExerciseTypes(ids: Collection<Uuid>)
}