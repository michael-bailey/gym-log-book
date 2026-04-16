@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.Deferred
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IMutableExerciseTypeRepository : IExerciseTypeRepository {
	fun createNewType(name: String, equipmentClass: EquipmentClass): Deferred<ExerciseType>
	suspend fun deleteTypes(ids: Collection<Uuid>)
}
