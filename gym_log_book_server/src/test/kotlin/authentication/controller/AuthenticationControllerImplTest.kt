@file:OptIn(ExperimentalUuidApi::class)

package authentication.controller

import io.mockk.*
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.authentication.controller.AuthenticationControllerImpl
import net.michael_bailey.gym_log_book.server.authentication.model.ViewerContext
import net.michael_bailey.gym_log_book.server.authentication.service.OldAuthenticationService
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationException
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AuthenticationControllerImplTest {

	private val oldAuthenticationService: OldAuthenticationService = mockk()

	@Test
	fun `getAuthenticationTokenPair throws when viewer is already authenticated`() = runTest {
		val controller = createController(
			viewerContext = ViewerContext(
				userId = Uuid.random(),
				username = USERNAME,
			)
		)

		assertFailsWith<AuthenticationException.AlreadyAuthenticated> {
			controller.getAuthenticationTokenPair(
				username = USERNAME,
				password = PASSWORD,
			)
		}

		coVerify(exactly = 0) {
			oldAuthenticationService.createTokenPair(any(), any())
		}
	}

	@Test
	fun `getAuthenticationTokenPair throws when service returns null`() = runTest {
		coEvery {
			oldAuthenticationService.createTokenPair(USERNAME, PASSWORD)
		} returns null

		val controller = createController()

		assertFailsWith<AuthenticationException.InvalidCredentials> {
			controller.getAuthenticationTokenPair(
				username = USERNAME,
				password = PASSWORD,
			)
		}

		coVerify(exactly = 1) {
			oldAuthenticationService.createTokenPair(USERNAME, PASSWORD)
		}
	}

	@Test
	fun `getAuthenticationTokenPair returns token pair when service succeeds`() = runTest {
		val expected = AuthenticationTokenPair(
			accessToken = ACCESS_TOKEN,
			refreshToken = REFRESH_TOKEN,
		)
		coEvery {
			oldAuthenticationService.createTokenPair(USERNAME, PASSWORD)
		} returns expected

		val controller = createController()

		val actual = controller.getAuthenticationTokenPair(
			username = USERNAME,
			password = PASSWORD,
		)

		assertEquals(expected, actual)
		coVerify(exactly = 1) {
			oldAuthenticationService.createTokenPair(USERNAME, PASSWORD)
		}
	}

	@Test
	fun `refreshAuthenticationTokenPair throws when service returns null`() = runTest {
		val tokenPair = AuthenticationTokenPair(
			accessToken = ACCESS_TOKEN,
			refreshToken = REFRESH_TOKEN,
		)
		every { oldAuthenticationService.refresh(tokenPair) } returns null

		val controller = createController()

		assertFailsWith<AuthenticationException.InvalidRefreshToken> {
			controller.refreshAuthenticationTokenPair(tokenPair)
		}

		verify(exactly = 1) { oldAuthenticationService.refresh(tokenPair) }
	}

	@Test
	fun `refreshAuthenticationTokenPair returns token pair when service succeeds`() = runTest {
		val tokenPair = AuthenticationTokenPair(
			accessToken = ACCESS_TOKEN,
			refreshToken = REFRESH_TOKEN,
		)
		val newTokenPair = AuthenticationTokenPair(
			accessToken = NEW_ACCESS_TOKEN,
			refreshToken = NEW_REFRESH_TOKEN,
		)
		every { oldAuthenticationService.refresh(tokenPair) } returns newTokenPair

		val controller = createController()

		val actual = controller.refreshAuthenticationTokenPair(tokenPair)

		assertEquals(newTokenPair, actual)
		verify(exactly = 1) { oldAuthenticationService.refresh(tokenPair) }
	}

	private fun createController(
		viewerContext: ViewerContext = ViewerContext(
			userId = Uuid.NIL,
			username = "",
		),
	): AuthenticationControllerImpl = AuthenticationControllerImpl(
		oldAuthenticationService = oldAuthenticationService,
	)

	private companion object {
		const val USERNAME = "test-user"
		const val PASSWORD = "test-password"
		const val ACCESS_TOKEN = "access-token"
		const val REFRESH_TOKEN = "refresh-token"
		const val NEW_ACCESS_TOKEN = "new-access-token"
		const val NEW_REFRESH_TOKEN = "new-refresh-token"
	}
}
