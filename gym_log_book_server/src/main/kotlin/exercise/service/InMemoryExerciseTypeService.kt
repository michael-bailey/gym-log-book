@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.server.exercise.repository.InMemoryExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory(binds = [IMutableExerciseTypeService::class, IExerciseTypeService::class])
class InMemoryExerciseTypeService(
	private val exerciseTypeRepository: InMemoryExerciseTypeRepository
) : IMutableExerciseTypeService {

	override val exerciseTypes: Flow<Collection<ExerciseType>> = exerciseTypeRepository.allExerciseTypes

	override suspend fun createNewExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	): ExerciseType = exerciseTypeRepository.createNewType(
		name = name,
		equipmentClass = equipmentClass
	).await()

	override suspend fun deleteExerciseTypes(ids: Collection<Uuid>) {
		exerciseTypeRepository.deleteTypes(ids)
	}
}
