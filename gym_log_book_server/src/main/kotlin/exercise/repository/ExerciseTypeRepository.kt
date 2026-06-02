@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.server.database.schema.v1.ExerciseTypeTable
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.mapper.toExerciseTypeDomain
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.insertReturning
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Single
class ExerciseTypeRepository(
	private val database: Database,
) : IMutableExerciseTypeRepository {

	private val _allExerciseTypes = MutableStateFlow<Collection<ExerciseTypeModel>>(emptyList())
	private val _exerciseTypeMap: Flow<Map<Uuid, ExerciseTypeModel>> = _allExerciseTypes
		.map { types -> types.associateBy { it.id } }

	override val allExerciseTypes: Flow<Collection<ExerciseTypeModel>>
		get() = _allExerciseTypes

	init {
		val initialItems = transaction(database) {
			ExerciseTypeTable.selectAll()
				.map { it.toExerciseTypeDomain() }
		}
		_allExerciseTypes.value = initialItems
	}

	override fun observeExerciseType(id: Uuid): Flow<ExerciseTypeModel?> = _exerciseTypeMap.map {
		it[id]
	}.distinctUntilChanged()

	override suspend fun getExerciseType(id: Uuid): ExerciseTypeModel? = transaction(database) {
		ExerciseTypeTable.selectAll()
			.where(ExerciseTypeTable.id eq id).limit(1)
			.firstOrNull()?.toExerciseTypeDomain()
	}

	override suspend fun getExerciseTypeByName(name: String): ExerciseTypeModel? = transaction(database) {
		ExerciseTypeTable.selectAll()
			.where(ExerciseTypeTable.name eq name).limit(1)
			.firstOrNull()?.toExerciseTypeDomain()
	}

	override suspend fun createType(newExerciseType: NewExerciseTypeModel): ExerciseTypeModel = transaction {
		val result = ExerciseTypeTable.insertReturning {
			it[this.name] = newExerciseType.name
			it[this.equipmentClass] = newExerciseType.equipmentClass.toString()
		}.first()

		result.toExerciseTypeDomain()
	}

	override suspend fun deleteTypes(ids: Collection<Uuid>) {
		TODO("Not yet implemented")
	}
}