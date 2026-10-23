@file:OptIn(ExperimentalUuidApi::class)

package authentication.service

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.authentication.model.TokenPayload
import net.michael_bailey.gym_log_book.server.authentication.service.AuthenticationService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AuthenticationServiceTest {

	@Test
	fun `isAuthenticated should emit false by default using Turbine`() = runTest {
		val authenticationService = AuthenticationService()

		authenticationService.isAuthenticated.test {
			val result = awaitItem()
			assertEquals(false, result)
			cancelAndIgnoreRemainingEvents()
		}
	}

	@Test
	fun `isAuthenticated should emit true after token is authenticated using Turbine`() = runTest {
		val authenticationService = AuthenticationService()

		// Authenticate with a test token
		authenticationService.authenticate(tokenPayload)

		authenticationService.isAuthenticated.test {
			val result = awaitItem()
			assertEquals(true, result)
			cancelAndIgnoreRemainingEvents()
		}
	}

	companion object {
		const val TEST_USERNAME = "Test User 1"

		val TEST_USER_ID = Uuid.generateV7()

		val tokenPayload = TokenPayload(
			subject = TEST_USER_ID,
			username = TEST_USERNAME
		)
	}
}