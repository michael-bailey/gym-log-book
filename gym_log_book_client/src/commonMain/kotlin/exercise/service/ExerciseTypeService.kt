@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseTypeService(
	private val typeRepository: ExerciseTypeRepository,
) {

	val exerciseTypes = typeRepository.allExerciseTypes
		.onEach { println("got types $it") }

	val exerciseNamesMap = exerciseTypes.map { coll -> coll.associate { it.id to it.name } }

	suspend fun getExerciseName(id: Uuid): String? = exerciseNamesMap.first()[id]

	suspend fun createNewType(
		name: String,
		equipmentClass: EquipmentClass,
	) {
		typeRepository.createNewExerciseType(
			name = name,
			equipmentClass = equipmentClass,
		)
	}

	suspend fun deleteTypes(ids: Collection<Uuid>) {
		typeRepository.deleteExerciseTypes(ids)
	}
}
