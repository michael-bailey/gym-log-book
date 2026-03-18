package counter.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.counter.repository.CounterRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class CounterRepositoryTest {
	@Test
	fun `repository increments over time`() = runTest {
		val scope = TestScope(StandardTestDispatcher(testScheduler))
		val repository = CounterRepository(scope, 100.milliseconds)

		assertEquals(0, repository.observeCounter().value)

		scope.advanceTimeBy(250)

		assertEquals(2, repository.observeCounter().value)

		scope.cancel()
	}
}
