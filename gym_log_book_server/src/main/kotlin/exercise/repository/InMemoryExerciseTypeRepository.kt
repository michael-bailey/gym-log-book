@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.factory.IExerciseTypeFactory
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import org.koin.core.annotation.Singleton
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Singleton(binds = [IMutableExerciseTypeRepository::class, IExerciseTypeRepository::class])
class InMemoryExerciseTypeRepository(
	private val exerciseTypeFactory: IExerciseTypeFactory
) : IMutableExerciseTypeRepository {

	private val _mapState = MutableStateFlow(buildMap<Uuid, ExerciseTypeModel> { seedData() })

	override val allExerciseTypes: Flow<Collection<ExerciseTypeModel>> = _mapState.map {
		it.values
	}

	override fun observeExerciseType(id: Uuid): Flow<ExerciseTypeModel?> = _mapState.map {
		it[id]
	}.distinctUntilChanged()

	override suspend fun getExerciseType(id: Uuid): ExerciseTypeModel? = _mapState.value[id]

	override suspend fun getExerciseTypeByName(name: String): ExerciseTypeModel? =
		_mapState.value.values.firstOrNull { it.name == name }

	override suspend fun createType(newExerciseType: NewExerciseTypeModel): ExerciseTypeModel {
		val type = exerciseTypeFactory.create(newExerciseType)

		_mapState.emit(_mapState.value + mapOf(type.id to type))

		return type
	}

	override suspend fun deleteTypes(ids: Collection<Uuid>) {
		if (ids.isEmpty()) {
			return
		}

		_mapState.emit(
			_mapState.value.filterKeys { id -> id !in ids }
		)
	}

	companion object {
		private fun MutableMap<Uuid, ExerciseTypeModel>.seedData() = (0 until 5).forEach {
			val id = Uuid.random()
			set(
				id, ExerciseTypeModel(
					id = id,
					name = "Type $it",
					equipmentClass = EquipmentClass.Machine,
				)
			)
		}
	}
}
