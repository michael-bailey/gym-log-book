@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package exercise.service

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.repository.IMutableExerciseTypeRepository
import net.michael_bailey.gym_log_book.server.exercise.service.InMemoryExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseTypeCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryExerciseTypeServiceTest {

	private val exerciseTypeRepository: IMutableExerciseTypeRepository = mockk()

	@Test
	fun `exerciseTypes maps domain models to shared models`() = runTest {
		val typeModel = createTypeModel()
		val exerciseTypesFlow = flowOf(listOf(typeModel))
		val service = createService(exerciseTypesFlow)

		assertEquals(listOf(createSharedType(id = typeModel.id)), service.exerciseTypes.first().toList())
	}

	@Test
	fun `createNewExerciseType trims name and delegates through newType`() = runTest {
		val createdType = createTypeModel(name = PULL_UP_NAME)
		coEvery { exerciseTypeRepository.getExerciseTypeByName(PULL_UP_NAME) } returns null
		coEvery {
			exerciseTypeRepository.createType(NewExerciseTypeModel(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS))
		} returns createdType

		val service = createService()

		val result = service.createNewExerciseType("  $PULL_UP_NAME  ", PULL_UP_EQUIPMENT_CLASS)

		assertEquals(createSharedType(id = createdType.id, name = PULL_UP_NAME), result)
		coVerify(exactly = 1) { exerciseTypeRepository.getExerciseTypeByName(PULL_UP_NAME) }
		coVerify(exactly = 1) {
			exerciseTypeRepository.createType(NewExerciseTypeModel(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS))
		}
	}

	@Test
	fun `newType creates a shared type when name is new`() = runTest {
		val command = NewExerciseTypeCommand(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)
		val createdType = createTypeModel()
		coEvery { exerciseTypeRepository.getExerciseTypeByName(PULL_UP_NAME) } returns null
		coEvery {
			exerciseTypeRepository.createType(NewExerciseTypeModel(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS))
		} returns createdType

		val service = createService()

		val result = service.newType(command)

		assertEquals(createSharedType(id = createdType.id), result)
	}

	@Test
	fun `newType throws when duplicate type exists`() = runTest {
		val command = NewExerciseTypeCommand(PULL_UP_NAME, PULL_UP_EQUIPMENT_CLASS)
		coEvery { exerciseTypeRepository.getExerciseTypeByName(PULL_UP_NAME) } returns createTypeModel()

		val service = createService()

		assertFailsWith<IllegalArgumentException> {
			service.newType(command)
		}
	}

	@Test
	fun `deleteExerciseTypes delegates to repository`() = runTest {
		val ids = listOf(Uuid.random(), Uuid.random())
		coEvery { exerciseTypeRepository.deleteTypes(ids) } returns Unit

		val service = createService()

		service.deleteExerciseTypes(ids)

		coVerify(exactly = 1) { exerciseTypeRepository.deleteTypes(ids) }
	}

	private fun createService(
		exerciseTypesFlow: Flow<Collection<ExerciseTypeModel>> = flowOf(emptyList())
	): InMemoryExerciseTypeService = InMemoryExerciseTypeService(
		exerciseTypeRepository = exerciseTypeRepository.also {
			every { it.allExerciseTypes } returns exerciseTypesFlow
		},
	)

	private fun createTypeModel(
		id: Uuid = Uuid.random(),
		name: String = PULL_UP_NAME,
		equipmentClass: EquipmentClass = PULL_UP_EQUIPMENT_CLASS,
	): ExerciseTypeModel = ExerciseTypeModel(
		id = id,
		name = name,
		equipmentClass = equipmentClass,
	)

	private fun createSharedType(
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
