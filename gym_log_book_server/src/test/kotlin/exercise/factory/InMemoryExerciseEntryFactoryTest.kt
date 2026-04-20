@file:OptIn(ExperimentalUuidApi::class)

package exercise.factory

import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.server.exercise.factory.InMemoryExerciseEntryFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryExerciseEntryFactoryTest {

	private val clock: Clock = mockk()
	private val factory = InMemoryExerciseEntryFactory(clock)

	@Test
	fun `createEntry maps inputs and uses UTC clock time for date`() {
		every { clock.now() } returns FIXED_INSTANT
		val exerciseTypeId = Uuid.random()

		val entry = factory.createEntry(
			exerciseTypeId = exerciseTypeId,
			entrySetNumber = SET_NUMBER,
			entryWeight = WEIGHT,
			entryReps = REPS,
		)

		assertEquals(exerciseTypeId, entry.exerciseTypeId)
		assertEquals(SET_NUMBER, entry.setNumber)
		assertEquals(WEIGHT, entry.weight)
		assertEquals(REPS, entry.reps)
		assertEquals(FIXED_INSTANT.toLocalDateTime(TimeZone.UTC), entry.date)
		assertNotEquals(Uuid.NIL, entry.id)
	}

	@Test
	fun `createEntry generates a new id for each entry`() {
		every { clock.now() } returns FIXED_INSTANT
		val exerciseTypeId = Uuid.random()

		val firstEntry = factory.createEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)
		val secondEntry = factory.createEntry(exerciseTypeId, SET_NUMBER, WEIGHT, REPS)

		assertNotEquals(Uuid.NIL, firstEntry.id)
		assertNotEquals(Uuid.NIL, secondEntry.id)
		assertNotEquals(firstEntry.id, secondEntry.id)
	}

	companion object {
		private val FIXED_INSTANT = Instant.parse("2025-01-02T03:04:05Z")
		private const val SET_NUMBER = 3
		private const val WEIGHT = 185.5
		private const val REPS = 8
	}
}
