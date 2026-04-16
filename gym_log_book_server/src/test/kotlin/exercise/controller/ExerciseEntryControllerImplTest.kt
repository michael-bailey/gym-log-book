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
import net.michael_bailey.gym_log_book.server.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryControllerImplTest {

	private val exerciseEntryService: ExerciseEntryService = mockk()

	@Test
	fun `getExerciseEntries delegates to service flow`() = runTest {
		val exerciseEntries = listOf(createEntry())
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

	private fun createController(
		exerciseEntriesFlow: Flow<Collection<ExerciseEntry>> = flowOf(emptyList())
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

	companion object {
		private const val SET_NUMBER = 3
		private const val WEIGHT = 185.5
		private const val REPS = 8
	}
}
