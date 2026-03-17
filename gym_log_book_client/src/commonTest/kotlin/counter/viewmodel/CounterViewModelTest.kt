package net.michael_bailey.gym_log_book.client.counter.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CounterViewModelTest {
	@Test
	fun `view model reflects streamed counter values`() = runTest {
		val service = CounterClientService(
			object : CounterController {
				override fun observeCounter() = flowOf(41, 42)
			}
		)
		val scope = TestScope(StandardTestDispatcher(testScheduler))
		val viewModel = CounterViewModel(service, scope)

		scope.advanceUntilIdle()

		assertEquals("42", viewModel.counterText().value)

		viewModel.close()
	}
}
