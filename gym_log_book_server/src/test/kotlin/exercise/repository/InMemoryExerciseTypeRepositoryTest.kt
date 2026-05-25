@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package exercise.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.factory.IExerciseTypeFactory
import net.michael_bailey.gym_log_book.server.exercise.repository.InMemoryExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.test.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryExerciseTypeRepositoryTest {

	private val exerciseTypeFactory: IExerciseTypeFactory = mockk(relaxed = true)

	@Test
	fun `repository seeds initial exercise types`() = runTest {
		val repository = createRepository()

		val exerciseTypes = repository.allExerciseTypes.first()

		assertEquals(INITIAL_TYPE_COUNT, exerciseTypes.size)
		assertEquals(
			(0 until INITIAL_TYPE_COUNT).map { "$SEEDED_TYPE_NAME_PREFIX$it" },
			exerciseTypes.map { it.name }.sorted()
		)
		assertTrueAllMachine(exerciseTypes)
	}

	@Test
	fun `getters return found and missing exercise types`() = runTest {
		val repository = createRepository()
		val existingType = repository.allExerciseTypes.first().first()

		assertEquals(existingType, repository.getExerciseType(existingType.id))
		assertNull(repository.getExerciseType(Uuid.random()))
		assertEquals(existingType, repository.getExerciseTypeByName(existingType.name))
		assertNull(repository.getExerciseTypeByName(MISSING_NAME))
	}

	@Test
	fun `createType uses factory and adds new exercise type`() = runTest {
		val newType = NewExerciseTypeModel(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)
		val createdType = createTypeModel(
			name = PULL_UP_NAME,
			equipmentClass = PULL_UP_EQUIPMENT_CLASS,
		)
		every { exerciseTypeFactory.create(newType) } returns createdType

		val repository = createRepository()

		val result = repository.createType(newType)
		val allTypes = repository.allExerciseTypes.first()

		assertEquals(createdType, result)
		assertEquals(createdType, repository.getExerciseType(createdType.id))
		assertEquals(createdType, repository.getExerciseTypeByName(PULL_UP_NAME))
		assertEquals(INITIAL_TYPE_COUNT + 1, allTypes.size)
		verify(exactly = 1) { exerciseTypeFactory.create(newType) }
	}

	@Test
	fun `observeExerciseType only emits when the requested exercise type changes`() = runTest {
		val newType = NewExerciseTypeModel(BENCH_PRESS_NAME, BENCH_PRESS_EQUIPMENT_CLASS)
		val createdType = createTypeModel(
			name = BENCH_PRESS_NAME,
			equipmentClass = BENCH_PRESS_EQUIPMENT_CLASS,
		)
		every { exerciseTypeFactory.create(newType) } returns createdType

		val repository = createRepository()
		val existingType = repository.allExerciseTypes.first().first()
		val observedValues = mutableListOf<ExerciseTypeModel?>()
		val collectJob = backgroundScope.launch(start = CoroutineStart.UNDISPATCHED) {
			repository.observeExerciseType(existingType.id).collect(observedValues::add)
		}

		advanceUntilIdle()
		assertEquals(listOf<ExerciseTypeModel?>(existingType), observedValues)

		repository.createType(newType)
		advanceUntilIdle()

		assertEquals(listOf<ExerciseTypeModel?>(existingType), observedValues)
		collectJob.cancel()
	}

	@Test
	fun `deleteTypes removes matching exercise types`() = runTest {
		val repository = createRepository()
		val initialTypes = repository.allExerciseTypes.first().toList()
		val idsToDelete = initialTypes.take(2).map { it.id }

		repository.deleteTypes(idsToDelete)

		val remainingTypes = repository.allExerciseTypes.first()

		assertEquals(INITIAL_TYPE_COUNT - idsToDelete.size, remainingTypes.size)
		assertTrue(idsToDelete.none { id -> remainingTypes.any { it.id == id } })
	}

	@Test
	fun `deleteTypes with empty ids leaves repository unchanged`() = runTest {
		val repository = createRepository()
		val initialTypes = repository.allExerciseTypes.first()

		repository.deleteTypes(emptyList())

		assertEquals(initialTypes, repository.allExerciseTypes.first())
	}

	private fun assertTrueAllMachine(exerciseTypes: Collection<ExerciseTypeModel>) {
		assertEquals(
			exerciseTypes.size,
			exerciseTypes.count { it.equipmentClass == EquipmentClass.Machine }
		)
		assertFalse(exerciseTypes.isEmpty())
	}

	private fun TestScope.createRepository(): InMemoryExerciseTypeRepository =
		InMemoryExerciseTypeRepository(
			exerciseTypeFactory = exerciseTypeFactory,
		)

	private fun createTypeModel(
		id: Uuid = Uuid.random(),
		name: String,
		equipmentClass: EquipmentClass,
	): ExerciseTypeModel = ExerciseTypeModel(
		id = id,
		name = name,
		equipmentClass = equipmentClass,
	)

	companion object {
		private const val INITIAL_TYPE_COUNT = 5
		private const val SEEDED_TYPE_NAME_PREFIX = "Type "
		private const val MISSING_NAME = "Missing"
		private const val PULL_UP_NAME = "Pull Up"
		private val PULL_UP_EQUIPMENT_CLASS = EquipmentClass.UserWeightMachine
		private const val BENCH_PRESS_NAME = "Bench Press"
		private val BENCH_PRESS_EQUIPMENT_CLASS = EquipmentClass.FreeWeight
	}
}
