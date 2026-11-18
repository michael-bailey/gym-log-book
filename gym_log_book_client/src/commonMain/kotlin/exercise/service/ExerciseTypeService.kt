@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseEntryDatabaseRepository
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseTypeRepository
import net.michael_bailey.gym_log_book.client.exercise.repository.ILocalExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseTypeService(
	private val typeRepository: ExerciseTypeRepository,
	private val localTypeRepository: ILocalExerciseTypeRepository,
	private val exerciseEntryRepository: ExerciseEntryDatabaseRepository,
) : INetworkExerciseTypeService, ILocalExerciseTypeService {

	override val networkExerciseTypes = typeRepository.allNetworkExerciseTypes
		.onEach { println("got types $it") }

	override val networkExerciseNamesMap = networkExerciseTypes.map { coll -> coll.associate { it.id to it.name } }

	override val localExerciseTypes: Flow<Collection<ExerciseTypeEntity>> =
		localTypeRepository.allLocalExerciseTypes
	override val localExerciseNamesMap: Flow<Map<Uuid, String>> =
		localTypeRepository.allLocalExerciseTypesMap.map(::mapToName)

	override suspend fun getExerciseName(id: Uuid): String? = networkExerciseNamesMap.first()[id]
	override suspend fun getLocalExerciseName(id: Uuid): String? = localExerciseNamesMap.first()[id]

	override suspend fun createNewType(
		name: String,
		equipmentClass: EquipmentClass,
	) {
		typeRepository.createNewExerciseType(
			name = name,
			equipmentClass = equipmentClass,
		)
	}

	override suspend fun deleteTypes(ids: Collection<Uuid>) {
		typeRepository.deleteExerciseTypes(ids)
		localTypeRepository.deleteExerciseTypes(ids)
	}

	// mark: - Local type methods
	override suspend fun createNewLocalType(name: String, equipmentClass: EquipmentClass) {
		localTypeRepository.createNewExerciseType(
			name = name,
			equipmentClass = equipmentClass
		)
	}

	override suspend fun updateLocalType() {
		TODO("Not yet implemented")
	}

	override suspend fun deleteLocalType(id: Uuid, replacement: Uuid) {
		when {
			id == replacement -> return
			!isTypeUsed(id) -> localTypeRepository.deleteEntity(id).await()
		}

		exerciseEntryRepository.allExerciseEntryIds
			.first()
			.map(exerciseEntryRepository::getEntry)
			.awaitAll()
			.map(updateTypeId(replacement))
			.map(exerciseEntryRepository::updateEntity)
			.awaitAll()

	}

	private fun updateTypeId(
		replacement: Uuid,
	): (ExerciseEntryEntity) -> ExerciseEntryEntity = {
		it.copy(
			exerciseTypeId = replacement
		)
	}

	private suspend fun isTypeUsed(id: Uuid): Boolean =
		exerciseEntryRepository.allExerciseEntryIds
			.first()
			.map(exerciseEntryRepository::getEntry)
			.awaitAll()
			.any { it.exerciseTypeId == id }


	override suspend fun deleteLocalTypes(ids: Uuid, replacementId: Uuid) {

	}

	private fun mapToName(map: Map<Uuid, ExerciseTypeEntity>): Map<Uuid, String> = map.mapValues { it.value.name }

}
