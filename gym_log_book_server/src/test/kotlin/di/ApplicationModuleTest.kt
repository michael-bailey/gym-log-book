package di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import net.michael_bailey.gym_log_book.server.di.ApplicationModule
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNotSame
import kotlin.test.assertTrue

class ApplicationModuleTest {

	private val applicationModule = ApplicationModule()

	@Test
	fun `coroutineScope returns an active scope`() {
		val coroutineScope = applicationModule.coroutineScope()

		val job = coroutineScope.job()

		assertNotNull(job)
		assertTrue(job.isActive)

		coroutineScope.cancel()
	}

	@Test
	fun `coroutineScope creates a new scope on each direct call`() {
		val firstScope = applicationModule.coroutineScope()
		val secondScope = applicationModule.coroutineScope()

		assertNotSame(firstScope, secondScope)
		assertNotSame(firstScope.job(), secondScope.job())

		firstScope.cancel()
		secondScope.cancel()
	}
}

private fun CoroutineScope.job(): Job? = coroutineContext[Job]
