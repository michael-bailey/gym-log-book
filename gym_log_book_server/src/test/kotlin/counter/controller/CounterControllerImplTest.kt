package counter.controller

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import net.michael_bailey.gym_log_book.server.counter.controller.CounterControllerImpl
import net.michael_bailey.gym_log_book.server.counter.repository.CounterRepository
import net.michael_bailey.gym_log_book.server.counter.service.CounterService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class CounterControllerImplTest {
	@Test
	fun `controller forwards service flow`() {
		val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
		val repository = CounterRepository(scope, 1.seconds)
		val service = CounterService(repository)
		val controller = CounterControllerImpl(service)

		assertEquals(0, runBlocking { controller.observeCounter().first() })

		scope.cancel()
	}
}
