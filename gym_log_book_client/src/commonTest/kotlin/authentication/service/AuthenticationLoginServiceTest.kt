package authentication.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationRepository
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationLoginService
import net.michael_bailey.gym_log_book.shared.authentication.controller.OldAuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticationLoginServiceTest {

	@Test
	fun `login requests token pair and stores it`() = runTest {
		val repository = AuthenticationRepository()
		val controller = FakeOldAuthenticationController(TOKEN_PAIR)
		val service = AuthenticationLoginService(
			oldAuthenticationController = controller,
			authenticationRepository = repository,
		)

		service.login(USERNAME, PASSWORD)

		assertEquals(USERNAME, controller.lastUsername)
		assertEquals(PASSWORD, controller.lastPassword)
		assertEquals(TOKEN_PAIR, repository.token.first())
	}

	private class FakeOldAuthenticationController(
		private val tokenPair: AuthenticationTokenPair,
	) : OldAuthenticationController {
		var lastUsername: String? = null
		var lastPassword: String? = null

		override suspend fun getAuthenticationTokenPair(
			username: String,
			password: String,
		): AuthenticationTokenPair {
			lastUsername = username
			lastPassword = password
			return tokenPair
		}

		override suspend fun refreshAuthenticationTokenPair(
			authenticationTokenPair: AuthenticationTokenPair,
		): AuthenticationTokenPair = error("Not needed for this test")
	}

	private companion object {
		const val USERNAME = "test-user"
		const val PASSWORD = "test-password"
		val TOKEN_PAIR = AuthenticationTokenPair(
			accessToken = "access-token",
			refreshToken = "refresh-token",
		)
	}
}
