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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import net.michael_bailey.gym_log_book.server.exercise.repository.IMutableWeightEntryRepository
import net.michael_bailey.gym_log_book.server.exercise.service.WeightService
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class WeightServiceTest {

	private val weightEntryRepository: IMutableWeightEntryRepository = mockk()

	@Test
	fun `allWeightEntries delegates to repository flow`() = runTest {
		val weightEntries = listOf(createEntry())
		val weightEntriesFlow = flowOf(weightEntries)
		val service = createService(weightEntriesFlow)

		assertSame(weightEntriesFlow, service.allWeightEntries)
		assertEquals(weightEntries, service.allWeightEntries.first())
	}

	@Test
	fun `addWeightEntry delegates to repository and awaits result`() = runTest {
		val date = LocalDate(2025, 1, 2)
		val weight = 185.5
		val createdEntry = createEntry(date, weight)
		every {
			weightEntryRepository.addWeightEntry(date, weight)
		} returns CompletableDeferred(createdEntry)

		val service = createService()

		val result = service.addWeightEntry(date, weight)

		assertEquals(createdEntry, result)
		verify(exactly = 1) {
			weightEntryRepository.addWeightEntry(date, weight)
		}
	}

	private fun createService(
		weightEntriesFlow: Flow<Collection<WeightEntry>> = flowOf(emptyList())
	): WeightService = WeightService(
		weightEntryRepository = weightEntryRepository.also {
			every { it.allWeightEntries } returns weightEntriesFlow
		}
	)

	private fun createEntry(
		date: LocalDate = LocalDate(2025, 1, 2),
		weight: Double = 185.5,
	): WeightEntry = WeightEntry(
		id = Uuid.random(),
		date = date.atTime(0, 0),
		weight = weight,
	)
}
