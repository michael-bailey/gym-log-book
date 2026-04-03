@file:OptIn(ExperimentalUuidApi::class)

package exercise.controller

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.exercise.controller.ExerciseTypeControllerImpl
import net.michael_bailey.gym_log_book.server.exercise.service.IMutableExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalCoroutinesApi::class)
class ExerciseTypeControllerImplTest {

	private val exerciseTypeService: IMutableExerciseTypeService = mockk()

	@Test
	fun `exerciseTypes delegates to service flow`() = runTest {
		val exerciseTypes = listOf(
			ExerciseType(
				id = Uuid.random(),
				name = PULL_UP_NAME,
				equipmentClass = PULL_UP_EQUIPMENT_CLASS,
				isUsingUserWeight = PULL_UP_IS_USING_USER_WEIGHT,
			)
		)
		val exerciseTypesFlow = flowOf(exerciseTypes)
		val controller = createController(exerciseTypesFlow)

		assertSame(exerciseTypesFlow, controller.exerciseTypes())
		assertEquals(exerciseTypes, controller.exerciseTypes().first())
	}

	@Test
	fun `createExerciseType delegates to service with undefined equipment class`() = runTest {
		val createdType = ExerciseType(
			id = Uuid.random(),
			name = PULL_UP_NAME,
			equipmentClass = UNDEFINED_EQUIPMENT_CLASS,
			isUsingUserWeight = PULL_UP_IS_USING_USER_WEIGHT,
		)
		coEvery {
			exerciseTypeService.createNewExerciseType(PULL_UP_NAME, UNDEFINED_EQUIPMENT_CLASS)
		} returns createdType

		val controller = createController()

		val result = controller.createExerciseType(PULL_UP_NAME)

		assertEquals(createdType, result)
		coVerify(exactly = 1) {
			exerciseTypeService.createNewExerciseType(PULL_UP_NAME, UNDEFINED_EQUIPMENT_CLASS)
		}
	}

	private fun createController(
		exerciseTypesFlow: Flow<Collection<ExerciseType>> = flowOf(emptyList())
	): ExerciseTypeControllerImpl = ExerciseTypeControllerImpl(
		exerciseTypeService = exerciseTypeService.also {
			every { it.exerciseTypes } returns exerciseTypesFlow
		},
	)

	companion object {
		private const val PULL_UP_NAME = "Pull Up"
		private val PULL_UP_EQUIPMENT_CLASS = EquipmentClass.UsesUserWeight
		private const val PULL_UP_IS_USING_USER_WEIGHT = true
		private val UNDEFINED_EQUIPMENT_CLASS = EquipmentClass.Undefined("oops: $PULL_UP_NAME")
	}
}
