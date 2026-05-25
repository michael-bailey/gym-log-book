@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.server.exercise.mapper.toDomain
import net.michael_bailey.gym_log_book.server.exercise.mapper.toShared
import net.michael_bailey.gym_log_book.server.exercise.repository.IMutableExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseTypeCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory(binds = [IMutableExerciseTypeService::class, IExerciseTypeService::class])
class InMemoryExerciseTypeService(
	private val exerciseTypeRepository: IMutableExerciseTypeRepository
) : IMutableExerciseTypeService {

	override val exerciseTypes: Flow<Collection<ExerciseType>> =
		exerciseTypeRepository.allExerciseTypes.map { exerciseTypes ->
			exerciseTypes.map { it.toShared() }
		}

	@Deprecated("Use `newType`.")
	override suspend fun createNewExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	): ExerciseType = newType(
		command = NewExerciseTypeCommand(
			name = name,
			equipmentClass = equipmentClass,
		)
	)

	override suspend fun newType(command: NewExerciseTypeCommand): ExerciseType {
		if (exerciseTypeRepository.getExerciseTypeByName(command.name.trim()) != null) {
			throw IllegalArgumentException("Exercise type '${command.name.trim()}' already exists.")
		}

		return exerciseTypeRepository.createType(command.toDomain()).toShared()
	}

	override suspend fun deleteExerciseTypes(ids: Collection<Uuid>) {
		exerciseTypeRepository.deleteTypes(ids)
	}
}
