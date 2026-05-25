@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package exercise.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.factory.InMemoryExerciseEntryFactory
import net.michael_bailey.gym_log_book.server.exercise.repository.InMemoryExerciseEntryRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant
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
	fun `createEntry uses factory and stores created entry`() = runTest {
		val newExerciseEntry = createNewEntryModel()
		val createdEntry = createEntryModel(exerciseTypeId = newExerciseEntry.exerciseTypeId)
		every { exerciseEntryFactory.createEntry(newExerciseEntry) } returns createdEntry

		val repository = createRepository()

		val result = repository.createEntry(newExerciseEntry)
		val storedEntries = repository.exerciseEntries.first()

		assertEquals(createdEntry, result)
		assertEquals(listOf(createdEntry), storedEntries.toList())
		verify(exactly = 1) { exerciseEntryFactory.createEntry(newExerciseEntry) }
	}

	@Test
	fun `createEntry appends entries in order`() = runTest {
		val firstNewEntry = createNewEntryModel(exerciseTypeId = Uuid.random(), setNumber = SET_NUMBER)
		val secondNewEntry = createNewEntryModel(exerciseTypeId = Uuid.random(), setNumber = NEXT_SET_NUMBER)
		val firstEntry = createEntryModel(exerciseTypeId = firstNewEntry.exerciseTypeId, setNumber = SET_NUMBER)
		val secondEntry = createEntryModel(exerciseTypeId = secondNewEntry.exerciseTypeId, setNumber = NEXT_SET_NUMBER)
		every { exerciseEntryFactory.createEntry(firstNewEntry) } returns firstEntry
		every { exerciseEntryFactory.createEntry(secondNewEntry) } returns secondEntry

		val repository = createRepository()

		repository.createEntry(firstNewEntry)
		repository.createEntry(secondNewEntry)

		assertEquals(listOf(firstEntry, secondEntry), repository.exerciseEntries.first().toList())
	}

	private fun createRepository(): InMemoryExerciseEntryRepository =
		InMemoryExerciseEntryRepository(
			exerciseEntryFactory = exerciseEntryFactory,
		)

	private fun createNewEntryModel(
		exerciseTypeId: Uuid = Uuid.random(),
		setNumber: Int = SET_NUMBER,
	): NewExerciseEntryModel = NewExerciseEntryModel(
		exerciseTypeId = exerciseTypeId,
		creationInstant = FIXED_INSTANT,
		setNumber = setNumber,
		weight = WEIGHT,
		reps = REPS,
	)

	private fun createEntryModel(
		exerciseTypeId: Uuid,
		setNumber: Int = SET_NUMBER,
	): ExerciseEntryModel = ExerciseEntryModel(
		id = Uuid.random(),
		exerciseTypeId = exerciseTypeId,
		creationInstant = FIXED_INSTANT,
		setNumber = setNumber,
		weight = WEIGHT,
		reps = REPS,
	)

	companion object {
		private val FIXED_INSTANT = Instant.parse("2025-01-02T03:04:05Z")
		private const val SET_NUMBER = 3
		private const val NEXT_SET_NUMBER = 4
		private const val WEIGHT = 185.5
		private const val REPS = 8
	}
}
