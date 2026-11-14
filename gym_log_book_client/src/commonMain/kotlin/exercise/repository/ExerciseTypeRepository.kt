@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity
import net.michael_bailey.gym_log_book.client.exercise.factory.IExerciseTypeEntityFactory
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseTypeCommand
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * # ExerciseTypeRepository
 *
 * This class has a reference to the type controller for now.
 * This is to keep compatibility whilst the sync engine is in development
 *
 * This makes this scoped to the authentication scope, which is not ideal
 * But, This will change, as the new sync stuff gets created
 */
class ExerciseTypeRepository(
	private val controller: Flow<ExerciseTypeController?>,
	private val coroutineScope: CoroutineScope,
	private val dao: ExerciseTypeDao,
	private val exerciseTypeEntityFactory: IExerciseTypeEntityFactory,
) : INetworkExerciseTypeRepository, ILocalExerciseTypeRepository {

	private val _exerciseTypeController = controller
		.stateIn(coroutineScope, SharingStarted.Eagerly, initialValue = null)

	override val allNetworkExerciseTypes = controller
		.flatMapLatest { it?.exerciseTypes() ?: flowOf(emptyList()) }
		.catch { emit(emptyList()) }

	override val allNetworkExerciseTypesMap = allNetworkExerciseTypes.map(::associateById)

	override val allLocalExerciseTypes: Flow<Collection<ExerciseTypeEntity>> =
		dao.allExerciseTypes()
	override val allLocalExerciseTypesMap: Flow<Map<Uuid, ExerciseTypeEntity>> =
		allLocalExerciseTypes.map(::associateEntityById)

	override suspend fun createNewExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	) {
		val entry = exerciseTypeEntityFactory.create(
			name = name,
			equipmentClass = equipmentClass
		)
		dao.createExerciseType(entry)
		_exerciseTypeController.value?.newType(
			NewExerciseTypeCommand(
				name = name,
				equipmentClass = equipmentClass
			)
		)
	}

	override suspend fun deleteExerciseTypes(ids: Collection<Uuid>) {
		_exerciseTypeController.value?.deleteExerciseTypes(
			ids
		)
	}

	private fun associateById(
		coll: Collection<ExerciseType>,
	): Map<Uuid, ExerciseType> = coll.associateBy { type -> type.id }

	private fun associateEntityById(
		coll: Collection<ExerciseTypeEntity>,
	): Map<Uuid, ExerciseTypeEntity> = coll.associateBy { type -> type.id }

}