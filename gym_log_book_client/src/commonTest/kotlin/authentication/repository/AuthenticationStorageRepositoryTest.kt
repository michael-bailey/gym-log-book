package authentication.repository

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationStorageRepository
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthenticationStorageRepositoryTest {

	@Test
	fun `Setting token emits the token clear token clears it`() = runTest {

		val underTest = AuthenticationStorageRepository()

		underTest.setToken(tokenPair)
		underTest.token.test {
			assertEquals(tokenPair, awaitItem())
		}

		underTest.clearToken()
		underTest.token.test {
			assertEquals(null, awaitItem())
		}

	}

	companion object {
		val tokenPair = AuthenticationTokenPair(
			accessToken = "Test Token",
			refreshToken = "Test Token"
		)
	}
}