package counter.service

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import net.michael_bailey.gym_log_book.server.counter.repository.CounterRepository
import net.michael_bailey.gym_log_book.server.counter.service.CounterService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class CounterServiceTest {
	@Test
	fun `service forwards repository flow`() {
		val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
		val repository = CounterRepository(scope, 1.seconds)
		val service = CounterService(repository)

		assertEquals(0, runBlocking { service.observeCounter().first() })

		scope.cancel()
	}
}
