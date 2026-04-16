@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package exercise.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import net.michael_bailey.gym_log_book.server.exercise.factory.InMemoryExerciseEntryFactory
import net.michael_bailey.gym_log_book.server.exercise.repository.InMemoryExerciseEntryRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryExerciseEntryRepositoryTest {

	private val exerciseEntryFactory: InMemoryExerciseEntryFactory = mockk()

	@Test
	fun `exerciseEntries is empty initially`() = runTest {
		val repository = createRepository()

		assertEquals(emptyList(), repository.exerciseEntries.first().toList())
	}

	@Test
	fun `createNewEntry uses factory and stores created entry`() = runTest {
		val exerciseTypeId = Uuid.random()
		val createdEntry = createEntry(
			exerciseTypeId = exerciseTypeId,
			setNumber = SET_NUMBER,
			weight = WEIGHT,
			reps = REPS,
		)
		every {
			exerciseEntryFactory.createEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)
		} returns createdEntry

		val repository = createRepository()

		val result = repository.createNewEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS).await()
		val storedEntries = repository.exerciseEntries.first()

		assertEquals(createdEntry, result)
		assertEquals(listOf(createdEntry), storedEntries.toList())
		verify(exactly = 1) {
			exerciseEntryFactory.createEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)
		}
	}

	@Test
	fun `createNewEntry accumulates entries in repository flow`() = runTest {
		val firstExerciseTypeId = Uuid.random()
		val secondExerciseTypeId = Uuid.random()
		val firstEntry = createEntry(
			exerciseTypeId = firstExerciseTypeId,
			setNumber = SET_NUMBER,
			weight = WEIGHT,
			reps = REPS,
		)
		val secondEntry = createEntry(
			exerciseTypeId = secondExerciseTypeId,
			setNumber = NEXT_SET_NUMBER,
			weight = NEXT_WEIGHT,
			reps = NEXT_REPS,
		)
		every {
			exerciseEntryFactory.createEntry(firstExerciseTypeId, SET_NUMBER, WEIGHT, REPS)
		} returns firstEntry
		every {
			exerciseEntryFactory.createEntry(secondExerciseTypeId, NEXT_SET_NUMBER, NEXT_WEIGHT, NEXT_REPS)
		} returns secondEntry

		val repository = createRepository()

		repository.createNewEntry(firstExerciseTypeId, SET_NUMBER, WEIGHT, REPS).await()
		repository.createNewEntry(secondExerciseTypeId, NEXT_SET_NUMBER, NEXT_WEIGHT, NEXT_REPS).await()

		assertEquals(setOf(firstEntry, secondEntry), repository.exerciseEntries.first().toSet())
		verify(exactly = 1) {
			exerciseEntryFactory.createEntry(firstExerciseTypeId, SET_NUMBER, WEIGHT, REPS)
		}
		verify(exactly = 1) {
			exerciseEntryFactory.createEntry(secondExerciseTypeId, NEXT_SET_NUMBER, NEXT_WEIGHT, NEXT_REPS)
		}
	}

	private fun TestScope.createRepository(): InMemoryExerciseEntryRepository =
		InMemoryExerciseEntryRepository(
			exerciseEntryFactory = exerciseEntryFactory,
			scope = backgroundScope,
		)

	private fun createEntry(
		exerciseTypeId: Uuid,
		setNumber: Int,
		weight: Double,
		reps: Int,
	): ExerciseEntry = ExerciseEntry(
		id = Uuid.random(),
		date = LocalDateTime(2025, 1, 2, 3, 4, 5),
		exerciseTypeId = exerciseTypeId,
		setNumber = setNumber,
		weight = weight,
		reps = reps,
	)

	companion object {
		private const val SET_NUMBER = 3
		private const val WEIGHT = 185.5
		private const val REPS = 8
		private const val NEXT_SET_NUMBER = 4
		private const val NEXT_WEIGHT = 195.0
		private const val NEXT_REPS = 6
	}
}
