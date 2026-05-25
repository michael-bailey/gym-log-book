@file:OptIn(ExperimentalUuidApi::class)

package exercise.factory

import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.factory.InMemoryExerciseEntryFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryExerciseEntryFactoryTest {

	private val factory = InMemoryExerciseEntryFactory()

	@Test
	fun `createEntry maps inputs`() {
		val exerciseTypeId = Uuid.random()
		val newExerciseEntry = NewExerciseEntryModel(
			exerciseTypeId = exerciseTypeId,
			creationInstant = FIXED_INSTANT,
			setNumber = SET_NUMBER,
			weight = WEIGHT,
			reps = REPS,
		)

		val entry = factory.createEntry(newExerciseEntry)

		assertEquals(exerciseTypeId, entry.exerciseTypeId)
		assertEquals(FIXED_INSTANT, entry.creationInstant)
		assertEquals(SET_NUMBER, entry.setNumber)
		assertEquals(WEIGHT, entry.weight)
		assertEquals(REPS, entry.reps)
		assertNotEquals(Uuid.NIL, entry.id)
	}

	@Test
	fun `createEntry generates a new id for each entry`() {
		val newExerciseEntry = NewExerciseEntryModel(
			exerciseTypeId = Uuid.random(),
			creationInstant = FIXED_INSTANT,
			setNumber = SET_NUMBER,
			weight = WEIGHT,
			reps = REPS,
		)

		val firstEntry = factory.createEntry(newExerciseEntry)
		val secondEntry = factory.createEntry(newExerciseEntry)

		assertNotEquals(firstEntry.id, secondEntry.id)
	}

	companion object {
		private val FIXED_INSTANT = Instant.parse("2025-01-02T03:04:05Z")
		private const val SET_NUMBER = 3
		private const val WEIGHT = 185.5
		private const val REPS = 8
	}
}
