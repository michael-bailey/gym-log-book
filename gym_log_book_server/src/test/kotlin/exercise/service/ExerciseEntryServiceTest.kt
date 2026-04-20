@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package exercise.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import net.michael_bailey.gym_log_book.server.exercise.repository.IExerciseEntryRepository
import net.michael_bailey.gym_log_book.server.exercise.repository.IExerciseTypeRepository
import net.michael_bailey.gym_log_book.server.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryServiceTest {

	private val exerciseEntryRepository: IExerciseEntryRepository = mockk()
	private val exerciseTypeRepository: IExerciseTypeRepository = mockk()

	@Test
	fun `exerciseEntries delegates to repository flow`() = runTest {
		val exerciseEntries = listOf(createEntry())
		val exerciseEntriesFlow = flowOf(exerciseEntries)
		val service = createService(exerciseEntriesFlow)

		assertSame(exerciseEntriesFlow, service.exerciseEntries)
		assertEquals(exerciseEntries, service.exerciseEntries.first())
	}

	@Test
	fun `createExerciseEntry delegates to repository and awaits result`() = runTest {
		val exerciseTypeId = Uuid.random()
		val createdEntry = createEntry(exerciseTypeId = exerciseTypeId)
		every {
			exerciseEntryRepository.createNewEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)
		} returns CompletableDeferred(createdEntry)

		val service = createService()

		val result = service.createExerciseEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)

		assertEquals(createdEntry, result)
		verify(exactly = 1) {
			exerciseEntryRepository.createNewEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)
		}
	}

	private fun createService(
		exerciseEntriesFlow: Flow<Collection<ExerciseEntry>> = flowOf(emptyList())
	): ExerciseEntryService = ExerciseEntryService(
		exerciseEntryRepository = exerciseEntryRepository.also {
			every { it.exerciseEntries } returns exerciseEntriesFlow
		},
		exerciseTypeRepository = exerciseTypeRepository,
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
