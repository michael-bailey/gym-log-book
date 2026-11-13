@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ILocalExerciseTypeService : IExerciseTypeService {
	val localExerciseTypes: Flow<Collection<ExerciseTypeEntity>>
	val localExerciseNamesMap: Flow<Map<Uuid, String>>

	suspend fun createNewLocalType(name: String, equipmentClass: EquipmentClass)
	suspend fun updateLocalType()
	suspend fun deleteLocalType()
	suspend fun getLocalExerciseName(id: Uuid): String?
}