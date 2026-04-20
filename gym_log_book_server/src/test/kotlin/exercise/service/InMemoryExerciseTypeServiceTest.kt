@file:OptIn(ExperimentalUuidApi::class)

package exercise.service

import io.mockk.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.exercise.repository.InMemoryExerciseTypeRepository
import net.michael_bailey.gym_log_book.server.exercise.service.InMemoryExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalCoroutinesApi::class)
class InMemoryExerciseTypeServiceTest {

	private val exerciseTypeRepository: InMemoryExerciseTypeRepository = mockk()

	@Test
	fun `exerciseTypes delegates to repository flow`() = runTest {
		val exerciseTypes = listOf(
			ExerciseType(
				id = Uuid.random(),
				name = PULL_UP_NAME,
				equipmentClass = PULL_UP_EQUIPMENT_CLASS,
				isUsingUserWeight = PULL_UP_IS_USING_USER_WEIGHT,
			)
		)
		val exerciseTypesFlow = flowOf(exerciseTypes)
		val service = createService(exerciseTypesFlow)

		assertSame(exerciseTypesFlow, service.exerciseTypes)
		assertEquals(exerciseTypes, service.exerciseTypes.first())
	}

	@Test
	fun `createNewExerciseType delegates to repository and awaits result`() = runTest {
		val createdType = ExerciseType(
			id = Uuid.random(),
			name = PULL_UP_NAME,
			equipmentClass = PULL_UP_EQUIPMENT_CLASS,
			isUsingUserWeight = PULL_UP_IS_USING_USER_WEIGHT,
		)
		every {
			exerciseTypeRepository.createNewType(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)
		} returns CompletableDeferred(createdType)

		val service = createService()

		val result = service.createNewExerciseType(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)

		assertEquals(createdType, result)
		verify(exactly = 1) {
			exerciseTypeRepository.createNewType(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)
		}
	}

	@Test
	fun `deleteExerciseTypes delegates to repository`() = runTest {
		val ids = listOf(Uuid.random(), Uuid.random())
		coEvery { exerciseTypeRepository.deleteTypes(ids) } returns Unit

		val service = createService()

		service.deleteExerciseTypes(ids)

		coVerify(exactly = 1) {
			exerciseTypeRepository.deleteTypes(ids)
		}
	}

	private fun createService(
		exerciseTypesFlow: Flow<Collection<ExerciseType>> = flowOf(emptyList())
	): InMemoryExerciseTypeService = InMemoryExerciseTypeService(
		exerciseTypeRepository = exerciseTypeRepository.also {
			every { it.allExerciseTypes } returns exerciseTypesFlow
		},
	)

	companion object {
		private const val PULL_UP_NAME = "Pull Up"
		private val PULL_UP_EQUIPMENT_CLASS = EquipmentClass.UsesUserWeight
		private const val PULL_UP_IS_USING_USER_WEIGHT = true
	}
}
