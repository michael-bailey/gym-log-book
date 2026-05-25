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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.repository.IExerciseEntryRepository
import net.michael_bailey.gym_log_book.server.exercise.repository.IExerciseTypeRepository
import net.michael_bailey.gym_log_book.server.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryServiceTest {

	private val exerciseEntryRepository: IExerciseEntryRepository = mockk()
	private val exerciseTypeRepository: IExerciseTypeRepository = mockk()
	private val clock: Clock = mockk()

	@Test
	fun `exerciseEntries maps repository flow into entry views and uses unknown fallback`() = runTest {
		val knownTypeId = Uuid.random()
		val unknownTypeId = Uuid.random()
		val exerciseEntries = listOf(
			createEntryModel(exerciseTypeId = knownTypeId, setNumber = SET_NUMBER),
			createEntryModel(exerciseTypeId = unknownTypeId, setNumber = NEXT_SET_NUMBER),
		)
		val exerciseTypes = listOf(
			createTypeModel(id = knownTypeId, name = "Bench Press"),
		)
		val service = createService(
			exerciseEntriesFlow = flowOf(exerciseEntries),
			exerciseTypesFlow = flowOf(exerciseTypes),
		)

		val result = service.exerciseEntries.first().toList()

		assertEquals(
			listOf(
				createEntryView(
					entry = exerciseEntries[0],
					exerciseTypeName = "Bench Press",
				),
				createEntryView(
					entry = exerciseEntries[1],
					exerciseTypeName = "Unknown Exercise Type",
				),
			),
			result,
		)
	}

	@Test
	fun `createExerciseEntry uses clock and creates the entry through repositories`() = runTest {
		val exerciseTypeId = Uuid.random()
		val createdEntry = createEntryModel(exerciseTypeId = exerciseTypeId)
		coEvery { exerciseTypeRepository.getExerciseType(exerciseTypeId) } returns createTypeModel(id = exerciseTypeId)
		coEvery {
			exerciseEntryRepository.createEntry(
				NewExerciseEntryModel(
					exerciseTypeId = exerciseTypeId,
					creationInstant = FIXED_INSTANT,
					setNumber = SET_NUMBER,
					weight = WEIGHT,
					reps = REPS,
				)
			)
		} returns createdEntry
		every { clock.now() } returns FIXED_INSTANT

		val service = createService()

		val result = service.createExerciseEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)

		assertEquals(createEntry(createdEntry), result)
		coVerify(exactly = 1) { exerciseTypeRepository.getExerciseType(exerciseTypeId) }
		coVerify(exactly = 1) {
			exerciseEntryRepository.createEntry(
				NewExerciseEntryModel(
					exerciseTypeId = exerciseTypeId,
					creationInstant = FIXED_INSTANT,
					setNumber = SET_NUMBER,
					weight = WEIGHT,
					reps = REPS,
				)
			)
		}
	}

	@Test
	fun `newEntry returns created shared entry when type exists`() = runTest {
		val exerciseTypeId = Uuid.random()
		val command = createNewEntryModel(exerciseTypeId = exerciseTypeId)
		val createdEntry = createEntryModel(
			exerciseTypeId = exerciseTypeId,
			creationInstant = command.creationInstant,
		)
		coEvery { exerciseTypeRepository.getExerciseType(exerciseTypeId) } returns createTypeModel(id = exerciseTypeId)
		coEvery { exerciseEntryRepository.createEntry(command) } returns createdEntry

		val service = createService()

		val result = service.newEntry(command.toCommand())

		assertEquals(createEntry(createdEntry), result)
	}

	@Test
	fun `newEntry throws when type does not exist`() = runTest {
		val exerciseTypeId = Uuid.random()
		val command = createNewEntryModel(exerciseTypeId = exerciseTypeId).toCommand()
		coEvery { exerciseTypeRepository.getExerciseType(exerciseTypeId) } returns null

		val service = createService()

		assertFailsWith<NoSuchElementException> {
			service.newEntry(command)
		}
	}

	private fun createService(
		exerciseEntriesFlow: Flow<Collection<ExerciseEntryModel>> = flowOf(emptyList()),
		exerciseTypesFlow: Flow<Collection<ExerciseTypeModel>> = flowOf(emptyList()),
	): ExerciseEntryService = ExerciseEntryService(
		exerciseEntryRepository = exerciseEntryRepository.also {
			every { it.exerciseEntries } returns exerciseEntriesFlow
		},
		exerciseTypeRepository = exerciseTypeRepository.also {
			every { it.allExerciseTypes } returns exerciseTypesFlow
		},
		clock = clock,
	)

	private fun createEntryModel(
		exerciseTypeId: Uuid = Uuid.random(),
		creationInstant: Instant = FIXED_INSTANT,
		setNumber: Int = SET_NUMBER,
		weight: Double = WEIGHT,
		reps: Int = REPS,
	): ExerciseEntryModel = ExerciseEntryModel(
		id = Uuid.random(),
		exerciseTypeId = exerciseTypeId,
		creationInstant = creationInstant,
		setNumber = setNumber,
		weight = weight,
		reps = reps,
	)

	private fun createEntry(entry: ExerciseEntryModel): ExerciseEntry = ExerciseEntry(
		id = entry.id,
		date = entry.creationInstant.toLocalDateTime(TimeZone.UTC),
		exerciseTypeId = entry.exerciseTypeId,
		setNumber = entry.setNumber,
		weight = entry.weight,
		reps = entry.reps,
	)

	private fun createEntryView(
		entry: ExerciseEntryModel,
		exerciseTypeName: String,
	): ExerciseEntryView = ExerciseEntryView(
		id = entry.id,
		date = entry.creationInstant.toLocalDateTime(TimeZone.UTC),
		exerciseTypeId = entry.exerciseTypeId,
		exerciseTypeName = exerciseTypeName,
		setNumber = entry.setNumber,
		weight = entry.weight,
		reps = entry.reps,
	)

	private fun createTypeModel(
		id: Uuid = Uuid.random(),
		name: String = "Bench Press",
	): ExerciseTypeModel = ExerciseTypeModel(
		id = id,
		name = name,
		equipmentClass = EquipmentClass.Machine,
	)

	private fun createNewEntryModel(
		exerciseTypeId: Uuid = Uuid.random(),
	): NewExerciseEntryModel = NewExerciseEntryModel(
		exerciseTypeId = exerciseTypeId,
		creationInstant = FIXED_INSTANT,
		setNumber = SET_NUMBER,
		weight = WEIGHT,
		reps = REPS,
	)

	private fun NewExerciseEntryModel.toCommand() =
		net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand(
			exerciseTypeId = exerciseTypeId,
			creationInstant = creationInstant,
			setNumber = setNumber,
			weight = weight,
			reps = reps,
		)

	companion object {
		private val FIXED_INSTANT = Instant.parse("2025-01-02T03:04:05Z")
		private const val SET_NUMBER = 3
		private const val NEXT_SET_NUMBER = 4
		private const val WEIGHT = 185.5
		private const val REPS = 8
	}
}
