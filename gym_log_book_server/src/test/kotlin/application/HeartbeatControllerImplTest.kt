@file:OptIn(ExperimentalCoroutinesApi::class)

package application

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.application.HeartbeatControllerImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class HeartbeatControllerImplTest {

	@Test
	fun `increments normally`() = runTest {

		val underTest = HeartbeatControllerImpl(this.backgroundScope)

		underTest.heartbeat().test {
			assertEquals(Long.MIN_VALUE, awaitItem())

			advanceTimeBy(1.seconds + 1.milliseconds)   // step past the delay (the boundary itself isn't run)
			assertEquals(Long.MIN_VALUE + 1, awaitItem())

			advanceTimeBy(1.seconds)
			assertEquals(Long.MIN_VALUE + 2, awaitItem())

			cancelAndIgnoreRemainingEvents()            // the flow is infinite, so end it explicitly
		}
	}

	@Test
	fun `end wraps around to start`() {

	}

}