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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import net.michael_bailey.gym_log_book.server.exercise.controller.WeightEntryControllerImpl
import net.michael_bailey.gym_log_book.server.exercise.service.IWeightService
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class WeightEntryControllerImplTest {

	private val weightService: IWeightService = mockk()

	@Test
	fun `getWeightEntries delegates to service flow`() = runTest {
		val weightEntries = listOf(createEntry())
		val weightEntriesFlow = flowOf(weightEntries)
		val controller = createController(weightEntriesFlow)

		assertSame(weightEntriesFlow, controller.getWeightEntries())
		assertEquals(weightEntries, controller.getWeightEntries().first())
	}

	@Test
	fun `addWeightEntry delegates to service`() = runTest {
		val date = LocalDate(2025, 1, 2)
		val weight = 185.5
		val createdEntry = createEntry(date, weight)
		coEvery {
			weightService.addWeightEntry(date, weight)
		} returns createdEntry

		val controller = createController()

		val result = controller.addWeightEntry(date, weight)

		assertEquals(createdEntry, result)
		coVerify(exactly = 1) {
			weightService.addWeightEntry(date, weight)
		}
	}

	private fun createController(
		weightEntriesFlow: Flow<Collection<WeightEntry>> = flowOf(emptyList())
	): WeightEntryControllerImpl = WeightEntryControllerImpl(
		weightService = weightService.also {
			every { it.allWeightEntries } returns weightEntriesFlow
		},
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
