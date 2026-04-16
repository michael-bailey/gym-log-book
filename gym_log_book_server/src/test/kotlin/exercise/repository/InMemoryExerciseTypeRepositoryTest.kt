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
import net.michael_bailey.gym_log_book.server.exercise.factory.IExerciseTypeFactory
import net.michael_bailey.gym_log_book.server.exercise.repository.InMemoryExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
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
	fun `async getters return found and missing exercise types`() = runTest {
		val repository = createRepository()
		val existingType = repository.allExerciseTypes.first().first()

		assertEquals(existingType, repository.getExerciseTypeAsync(existingType.id))
		assertNull(repository.getExerciseTypeAsync(Uuid.random()))
		assertEquals(existingType, repository.getExerciseTypeByNameAsync(existingType.name))
		assertNull(repository.getExerciseTypeByNameAsync(MISSING_NAME))
	}

	@Test
	fun `createNewType uses factory and adds new exercise type`() = runTest {
		val createdType = ExerciseType(
			id = Uuid.random(),
			name = PULL_UP_NAME,
			equipmentClass = PULL_UP_EQUIPMENT_CLASS,
			isUsingUserWeight = PULL_UP_IS_USING_USER_WEIGHT,
		)
		every { exerciseTypeFactory.create(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS) } returns createdType

		val repository = createRepository()

		val result = repository.createNewType(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS).await()
		val allTypes = repository.allExerciseTypes.first()

		assertEquals(createdType, result)
		assertNotNull(repository.getExerciseTypeAsync(createdType.id))
		assertEquals(createdType, repository.getExerciseTypeByNameAsync(PULL_UP_NAME))
		assertEquals(INITIAL_TYPE_COUNT + 1, allTypes.size)
		assertEquals(createdType, allTypes.first { it.id == createdType.id })
		verify(exactly = 1) { exerciseTypeFactory.create(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS) }
	}

	@Test
	fun `getExerciseType only emits when the requested exercise type changes`() = runTest {
		val createdType = ExerciseType(
			id = Uuid.random(),
			name = BENCH_PRESS_NAME,
			equipmentClass = BENCH_PRESS_EQUIPMENT_CLASS,
			isUsingUserWeight = BENCH_PRESS_IS_USING_USER_WEIGHT,
		)
		every { exerciseTypeFactory.create(BENCH_PRESS_NAME, BENCH_PRESS_EQUIPMENT_CLASS) } returns createdType

		val repository = createRepository()
		val existingType = repository.allExerciseTypes.first().first()
		val observedValues = mutableListOf<ExerciseType?>()
		val collectJob = backgroundScope.launch(start = CoroutineStart.UNDISPATCHED) {
			repository.getExerciseType(existingType.id).collect(observedValues::add)
		}

		advanceUntilIdle()
		assertEquals(listOf<ExerciseType?>(existingType), observedValues)

		repository.createNewType(BENCH_PRESS_NAME, BENCH_PRESS_EQUIPMENT_CLASS).await()
		advanceUntilIdle()

		assertEquals(listOf<ExerciseType?>(existingType), observedValues)

		collectJob.cancel()
		verify(exactly = 1) { exerciseTypeFactory.create(BENCH_PRESS_NAME, BENCH_PRESS_EQUIPMENT_CLASS) }
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

	private fun assertTrueAllMachine(exerciseTypes: Collection<ExerciseType>) {
		assertEquals(
			exerciseTypes.size,
			exerciseTypes.count {
				it.equipmentClass == EquipmentClass.Machine && !it.isUsingUserWeight
			}
		)
		assertFalse(exerciseTypes.isEmpty())
	}

	private fun TestScope.createRepository(): InMemoryExerciseTypeRepository =
		InMemoryExerciseTypeRepository(
			scope = backgroundScope,
			exerciseTypeFactory = exerciseTypeFactory,
		)

	companion object {
		private const val INITIAL_TYPE_COUNT = 5
		private const val SEEDED_TYPE_NAME_PREFIX = "Type "
		private const val MISSING_NAME = "Missing"
		private const val PULL_UP_NAME = "Pull Up"
		private val PULL_UP_EQUIPMENT_CLASS = EquipmentClass.UsesUserWeight
		private const val PULL_UP_IS_USING_USER_WEIGHT = true
		private const val BENCH_PRESS_NAME = "Bench Press"
		private val BENCH_PRESS_EQUIPMENT_CLASS = EquipmentClass.FreeWeight
		private const val BENCH_PRESS_IS_USING_USER_WEIGHT = false
	}
}
