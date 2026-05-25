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
import kotlinx.datetime.LocalDateTime
import net.michael_bailey.gym_log_book.server.exercise.controller.ExerciseEntryControllerImpl
import net.michael_bailey.gym_log_book.server.exercise.service.IExerciseEntryService
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryControllerImplTest {

	private val exerciseEntryService: IExerciseEntryService = mockk()

	@Test
	fun `getExerciseEntries delegates to service flow`() = runTest {
		val exerciseEntries = listOf(createEntryView())
		val exerciseEntriesFlow = flowOf(exerciseEntries)
		val controller = createController(exerciseEntriesFlow)

		assertSame(exerciseEntriesFlow, controller.getExerciseEntries())
		assertEquals(exerciseEntries, controller.getExerciseEntries().first())
	}

	@Test
	fun `createEntry delegates to service`() = runTest {
		val exerciseTypeId = Uuid.random()
		val createdEntry = createEntry(exerciseTypeId = exerciseTypeId)
		coEvery {
			exerciseEntryService.createExerciseEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)
		} returns createdEntry

		val controller = createController()

		val result = controller.createEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)

		assertEquals(createdEntry, result)
		coVerify(exactly = 1) {
			exerciseEntryService.createExerciseEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)
		}
	}

	@Test
	fun `newEntry delegates to service`() = runTest {
		val command = NewExerciseEntryCommand(
			exerciseTypeId = Uuid.random(),
			creationInstant = Instant.parse("2025-01-02T03:04:05Z"),
			setNumber = SET_NUMBER,
			weight = WEIGHT,
			reps = REPS,
		)
		val createdEntry = createEntry(exerciseTypeId = command.exerciseTypeId)
		coEvery { exerciseEntryService.newEntry(command) } returns createdEntry

		val controller = createController()

		val result = controller.newEntry(command)

		assertEquals(createdEntry, result)
		coVerify(exactly = 1) { exerciseEntryService.newEntry(command) }
	}

	private fun createController(
		exerciseEntriesFlow: Flow<Collection<ExerciseEntryView>> = flowOf(emptyList())
	): ExerciseEntryControllerImpl = ExerciseEntryControllerImpl(
		exerciseEntryService = exerciseEntryService.also {
			every { it.exerciseEntries } returns exerciseEntriesFlow
		},
	)

	private fun createEntry(
		exerciseTypeId: Uuid = Uuid.random(),
	): ExerciseEntry = ExerciseEntry(
		id = Uuid.random(),
		date = LocalDateTime(2025, 1, 2, 3, 4, 5),
		exerciseTypeId = exerciseTypeId,
		setNumber = SET_NUMBER,
		weight = WEIGHT,
		reps = REPS,
	)

	private fun createEntryView(
		exerciseTypeId: Uuid = Uuid.random(),
	): ExerciseEntryView = ExerciseEntryView(
		id = Uuid.random(),
		date = LocalDateTime(2025, 1, 2, 3, 4, 5),
		exerciseTypeId = exerciseTypeId,
		exerciseTypeName = "Bench Press",
		setNumber = SET_NUMBER,
		weight = WEIGHT,
		reps = REPS,
	)

	companion object {
		private const val SET_NUMBER = 3
		private const val WEIGHT = 185.5
		private const val REPS = 8
	}
}
