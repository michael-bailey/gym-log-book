@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package exercise.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import net.michael_bailey.gym_log_book.server.exercise.factory.IWeightEntryFactory
import net.michael_bailey.gym_log_book.server.exercise.repository.InMemoryWeightEntryRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryWeightEntryRepositoryTest {

	private val weightEntryFactory: IWeightEntryFactory = mockk()

	@Test
	fun `allWeightEntries is empty initially`() = runTest {
		val repository = createRepository()

		assertEquals(emptyList(), repository.allWeightEntries.first().toList())
	}

	@Test
	fun `addWeightEntry uses factory and stores created entry`() = runTest {
		val date = LocalDate(2025, 1, 2)
		val weight = 185.5
		val createdEntry = createEntry(date, weight)
		every {
			weightEntryFactory.createEntry(date, weight)
		} returns createdEntry

		val repository = createRepository()

		val result = repository.addWeightEntry(date, weight).await()
		val storedEntries = repository.allWeightEntries.first()

		assertEquals(createdEntry, result)
		assertEquals(listOf(createdEntry), storedEntries.toList())
		verify(exactly = 1) {
			weightEntryFactory.createEntry(date, weight)
		}
	}

	@Test
	fun `addWeightEntry accumulates entries in repository flow`() = runTest {
		val date1 = LocalDate(2025, 1, 2)
		val weight1 = 185.5
		val date2 = LocalDate(2025, 1, 3)
		val weight2 = 186.0

		val entry1 = createEntry(date1, weight1)
		val entry2 = createEntry(date2, weight2)

		every { weightEntryFactory.createEntry(date1, weight1) } returns entry1
		every { weightEntryFactory.createEntry(date2, weight2) } returns entry2

		val repository = createRepository()

		repository.addWeightEntry(date1, weight1).await()
		repository.addWeightEntry(date2, weight2).await()

		assertEquals(setOf(entry1, entry2), repository.allWeightEntries.first().toSet())
	}

	private fun TestScope.createRepository(): InMemoryWeightEntryRepository =
		InMemoryWeightEntryRepository(
			weightEntryFactory = weightEntryFactory,
			scope = backgroundScope,
		)

	private fun createEntry(
		date: LocalDate,
		weight: Double,
	): WeightEntry = WeightEntry(
		id = Uuid.random(),
		date = date.atTime(0, 0),
		weight = weight,
	)
}
