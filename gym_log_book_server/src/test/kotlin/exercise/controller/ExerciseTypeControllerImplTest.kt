@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

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
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseTypeCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseTypeControllerImplTest {

	private val exerciseTypeService: IMutableExerciseTypeService = mockk()

	@Test
	fun `exerciseTypes delegates to service flow`() = runTest {
		val exerciseTypes = listOf(createType())
		val exerciseTypesFlow = flowOf(exerciseTypes)
		val controller = createController(exerciseTypesFlow)

		assertSame(exerciseTypesFlow, controller.exerciseTypes())
		assertEquals(exerciseTypes, controller.exerciseTypes().first())
	}

	@Test
	fun `createExerciseType delegates to service with the provided equipment class`() = runTest {
		val createdType = createType()
		coEvery {
			exerciseTypeService.createNewExerciseType(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)
		} returns createdType

		val controller = createController()

		val result = controller.createExerciseType(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)

		assertEquals(createdType, result)
		coVerify(exactly = 1) {
			exerciseTypeService.createNewExerciseType(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)
		}
	}

	@Test
	fun `newType delegates to service`() = runTest {
		val command = NewExerciseTypeCommand(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)
		val createdType = createType()
		coEvery { exerciseTypeService.newType(command) } returns createdType

		val controller = createController()

		val result = controller.newType(command)

		assertEquals(createdType, result)
		coVerify(exactly = 1) { exerciseTypeService.newType(command) }
	}

	@Test
	fun `deleteExerciseTypes delegates to service`() = runTest {
		val ids = listOf(Uuid.random(), Uuid.random())
		coEvery { exerciseTypeService.deleteExerciseTypes(ids) } returns Unit

		val controller = createController()

		controller.deleteExerciseTypes(ids)

		coVerify(exactly = 1) {
			exerciseTypeService.deleteExerciseTypes(ids)
		}
	}

	private fun createController(
		exerciseTypesFlow: Flow<Collection<ExerciseType>> = flowOf(emptyList())
	): ExerciseTypeControllerImpl = ExerciseTypeControllerImpl(
		exerciseTypeService = exerciseTypeService.also {
			every { it.exerciseTypes } returns exerciseTypesFlow
		},
	)

	private fun createType(
		id: Uuid = Uuid.random(),
		name: String = PULL_UP_NAME,
		equipmentClass: EquipmentClass = PULL_UP_EQUIPMENT_CLASS,
	): ExerciseType = ExerciseType(
		id = id,
		name = name,
		equipmentClass = equipmentClass,
	)

	companion object {
		private const val PULL_UP_NAME = "Pull Up"
		private val PULL_UP_EQUIPMENT_CLASS = EquipmentClass.UserWeightMachine
	}
}
