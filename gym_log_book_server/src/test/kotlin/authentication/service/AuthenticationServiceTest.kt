@file:OptIn(ExperimentalUuidApi::class)

package authentication.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.michael_bailey.gym_log_book.server.authentication.model.TokenPayload
import net.michael_bailey.gym_log_book.server.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.server.authentication.service.ITokenService
import net.michael_bailey.gym_log_book.server.authentication.service.IUsernamePasswordService
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AuthenticationServiceTest {

	private val tokenService: ITokenService = mockk()
	private val usernamePasswordService: IUsernamePasswordService = mockk()

	private val service = AuthenticationService(
		tokenService = tokenService,
		usernamePasswordService = usernamePasswordService,
	)

	@Test
	fun `test createTokenPair with invalid credentials returns null`() {
		every {
			usernamePasswordService.validateUsernameAndPassword(USERNAME, PASSWORD)
		} returns false

		val actual = service.createTokenPair(
			username = USERNAME,
			password = PASSWORD,
		)

		assertNull(actual)
		verify(exactly = 1) {
			usernamePasswordService.validateUsernameAndPassword(USERNAME, PASSWORD)
		}
		verify(exactly = 0) { tokenService.issueAccessToken(any(), any()) }
		verify(exactly = 0) { tokenService.issueRefreshToken(any(), any()) }
	}

	@Test
	fun `test createTokenPair with valid credentials returns token pair`() {
		val expectedTokenPair = AuthenticationTokenPair(
			accessToken = ACCESS_TOKEN,
			refreshToken = REFRESH_TOKEN,
		)

		every {
			usernamePasswordService.validateUsernameAndPassword(USERNAME, PASSWORD)
		} returns true
		every {
			tokenService.issueAccessToken(any(), USERNAME)
		} returns ACCESS_TOKEN
		every {
			tokenService.issueRefreshToken(any(), USERNAME)
		} returns REFRESH_TOKEN

		val actual = service.createTokenPair(
			username = USERNAME,
			password = PASSWORD,
		)

		assertEquals(expectedTokenPair, actual)
		verify(exactly = 1) {
			usernamePasswordService.validateUsernameAndPassword(USERNAME, PASSWORD)
		}
		verify(exactly = 1) { tokenService.issueAccessToken(any(), USERNAME) }
		verify(exactly = 1) { tokenService.issueRefreshToken(any(), USERNAME) }
	}

	@Test
	fun `test refresh with invalid refresh token returns null`() {
		every {
			tokenService.verifyRefreshToken(OLD_REFRESH_TOKEN)
		} returns null

		val actual = service.refresh(
			tokenPair = AuthenticationTokenPair(
				accessToken = OLD_ACCESS_TOKEN,
				refreshToken = OLD_REFRESH_TOKEN,
			)
		)

		assertNull(actual)
		verify(exactly = 1) {
			tokenService.verifyRefreshToken(OLD_REFRESH_TOKEN)
		}
		verify(exactly = 0) { tokenService.issueAccessToken(any(), any()) }
		verify(exactly = 0) { tokenService.issueRefreshToken(any(), any()) }
	}

	@Test
	fun `test refresh with valid refresh token returns newly issued token pair`() {
		val expectedUserId = Uuid.random()
		every {
			tokenService.verifyRefreshToken(OLD_REFRESH_TOKEN)
		} returns TokenPayload(
			subject = expectedUserId,
			username = USERNAME,
		)
		every {
			tokenService.issueAccessToken(expectedUserId, USERNAME)
		} returns ACCESS_TOKEN
		every {
			tokenService.issueRefreshToken(expectedUserId, USERNAME)
		} returns REFRESH_TOKEN

		val actual = service.refresh(
			tokenPair = AuthenticationTokenPair(
				accessToken = OLD_ACCESS_TOKEN,
				refreshToken = OLD_REFRESH_TOKEN,
			)
		)

		assertEquals(
			expected = AuthenticationTokenPair(
				accessToken = ACCESS_TOKEN,
				refreshToken = REFRESH_TOKEN,
			),
			actual = actual,
		)
		verify(exactly = 1) {
			tokenService.verifyRefreshToken(OLD_REFRESH_TOKEN)
		}
		verify(exactly = 1) {
			tokenService.issueAccessToken(expectedUserId, USERNAME)
		}
		verify(exactly = 1) {
			tokenService.issueRefreshToken(expectedUserId, USERNAME)
		}
	}

	private companion object {
		const val USERNAME = "test-user"
		const val PASSWORD = "test-password"
		const val OLD_ACCESS_TOKEN = "old-access-token"
		const val OLD_REFRESH_TOKEN = "old-refresh-token"
		const val ACCESS_TOKEN = "issued-access-token"
		const val REFRESH_TOKEN = "issued-refresh-token"
	}
}
