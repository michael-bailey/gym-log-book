@file:OptIn(ExperimentalUuidApi::class)

package exercise.factory

import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import net.michael_bailey.gym_log_book.server.exercise.factory.InMemoryWeightEntryFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryWeightEntryFactoryTest {

	private val factory = InMemoryWeightEntryFactory()

	@Test
	fun `createEntry maps inputs and uses provided date at start of day`() {
		val date = LocalDate(2025, 1, 2)
		val weight = 185.5

		val entry = factory.createEntry(
			date = date,
			weight = weight
		)

		assertEquals(date.atTime(0, 0), entry.date)
		assertEquals(weight, entry.weight)
		assertNotEquals(Uuid.NIL, entry.id)
	}

	@Test
	fun `createEntry generates a new id for each entry`() {
		val date = LocalDate(2025, 1, 2)
		val weight = 185.5

		val firstEntry = factory.createEntry(date, weight)
		val secondEntry = factory.createEntry(date, weight)

		assertNotEquals(Uuid.NIL, firstEntry.id)
		assertNotEquals(Uuid.NIL, secondEntry.id)
		assertNotEquals(firstEntry.id, secondEntry.id)
	}
}
