@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.server.exercise.factory.IExerciseTypeFactory
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import org.koin.core.annotation.Singleton
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Singleton(binds = [IMutableExerciseTypeRepository::class, IExerciseTypeRepository::class])
class InMemoryExerciseTypeRepository(
	private val scope: CoroutineScope,
	private val exerciseTypeFactory: IExerciseTypeFactory
) : IMutableExerciseTypeRepository {

	private val _mapState: MutableStateFlow<Map<Uuid, ExerciseType>> = MutableStateFlow(buildMap { seedData() })

	override val allExerciseTypes: Flow<Collection<ExerciseType>> = _mapState.map {
		it.values
	}

	override fun getExerciseType(id: Uuid): Flow<ExerciseType?> = _mapState.map {
		it[id]
	}.distinctUntilChanged()

	override suspend fun getExerciseTypeAsync(id: Uuid): ExerciseType? = _mapState.value[id]

	override suspend fun getExerciseTypeByNameAsync(name: String): ExerciseType? =
		_mapState.value.values.firstOrNull { it.name == name }

	override fun createNewType(
		name: String,
		equipmentClass: EquipmentClass,
	): Deferred<ExerciseType> = scope.async {
		val type = exerciseTypeFactory.create(
			name = name,
			equipmentClass = equipmentClass,
		)

		_mapState.emit(_mapState.value + mapOf(type.id to type))

		type
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
		private fun MutableMap<Uuid, ExerciseType>.seedData() = (0 until 5).forEach {
			val id = Uuid.random()
			set(
				id, ExerciseType(
					id = id,
					name = "Type $it",
					equipmentClass = EquipmentClass.Machine,
					isUsingUserWeight = false
				)
			)
		}
	}
}
