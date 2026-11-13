@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseTypeService {
	suspend fun getExerciseName(id: Uuid): String?

	suspend fun createNewType(
		name: String,
		equipmentClass: EquipmentClass,
	)

	suspend fun deleteTypes(ids: Collection<Uuid>)
}