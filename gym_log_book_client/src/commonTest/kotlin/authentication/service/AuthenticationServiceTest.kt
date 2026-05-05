package authentication.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationRepository
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticationServiceTest {

	@Test
	fun `isAuthenticated is false when repository has no token`() = runTest {
		val service = AuthenticationService(
			authenticationRepository = AuthenticationRepository(),
			authenticationController = FakeAuthenticationController(),
		)

		assertFalse(service.isAuthenticated.first())
	}

	@Test
	fun `isAuthenticated becomes true after token is stored`() = runTest {
		val repository = AuthenticationRepository()
		val service = AuthenticationService(
			authenticationRepository = repository,
			authenticationController = FakeAuthenticationController(),
		)

		repository.setToken(TOKEN_PAIR)

		assertTrue(service.isAuthenticated.first())
	}

	@Test
	fun `logout clears stored token`() = runTest {
		val repository = AuthenticationRepository()
		repository.setToken(TOKEN_PAIR)

		val service = AuthenticationService(
			authenticationRepository = repository,
			authenticationController = FakeAuthenticationController(),
		)

		service.logout()

		assertNull(repository.token.first())
		assertFalse(service.isAuthenticated.first())
	}

	@Test
	fun `refreshTokens returns null when no token exists`() = runTest {
		val controller = FakeAuthenticationController(
			refreshedTokenPair = REFRESHED_TOKEN_PAIR,
		)
		val service = AuthenticationService(
			authenticationRepository = AuthenticationRepository(),
			authenticationController = controller,
		)

		val actual = service.refreshTokens()

		assertNull(actual)
		assertNull(controller.lastRefreshRequest)
	}

	@Test
	fun `refreshTokens calls controller and persists refreshed token`() = runTest {
		val repository = AuthenticationRepository()
		repository.setToken(TOKEN_PAIR)
		val controller = FakeAuthenticationController(
			refreshedTokenPair = REFRESHED_TOKEN_PAIR,
		)
		val service = AuthenticationService(
			authenticationRepository = repository,
			authenticationController = controller,
		)

		val actual = service.refreshTokens()

		assertEquals(TOKEN_PAIR, controller.lastRefreshRequest)
		assertEquals(REFRESHED_TOKEN_PAIR, actual)
		assertEquals(REFRESHED_TOKEN_PAIR, repository.token.first())
	}

	private class FakeAuthenticationController(
		private val refreshedTokenPair: AuthenticationTokenPair? = null,
	) : AuthenticationController {
		var lastRefreshRequest: AuthenticationTokenPair? = null

		override suspend fun getAuthenticationTokenPair(
			username: String,
			password: String,
		): AuthenticationTokenPair = error("Not needed for this test")

		override suspend fun refreshAuthenticationTokenPair(
			authenticationTokenPair: AuthenticationTokenPair,
		): AuthenticationTokenPair {
			lastRefreshRequest = authenticationTokenPair
			return refreshedTokenPair ?: error("No refreshed token configured")
		}
	}

	private companion object {
		val TOKEN_PAIR = AuthenticationTokenPair(
			accessToken = "access-token",
			refreshToken = "refresh-token",
		)
		val REFRESHED_TOKEN_PAIR = AuthenticationTokenPair(
			accessToken = "new-access-token",
			refreshToken = "new-refresh-token",
		)
	}
}
