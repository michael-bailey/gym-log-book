@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package authentication.controller

import com.auth0.jwt.exceptions.*
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.authentication.controller.AuthenticationControllerImpl
import net.michael_bailey.gym_log_book.server.authentication.model.TokenPayload
import net.michael_bailey.gym_log_book.server.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.server.authentication.service.ITokenService
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationException
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AuthenticationControllerImplTest {

	private val authenticationService: AuthenticationService = mockk()
	private val tokenService: ITokenService = mockk()

	@Test
	fun `isAuthenticated returns the service flow`() = runTest {
		val authenticatedFlow = flowOf(true)
		every { authenticationService.isAuthenticated } returns authenticatedFlow
		val controller = createController()

		val actual = controller.isAuthenticated()

		assertSame(authenticatedFlow, actual)
		assertEquals(true, actual.first())
		verify(exactly = 1) { authenticationService.isAuthenticated }
	}

	@Test
	fun `authenticate verifies the access token and returns success`() = runTest {
		val payload = TokenPayload(subject = Uuid.NIL, username = USERNAME)
		every { tokenService.verifyAccessToken(ACCESS_TOKEN) } returns payload
		coEvery { authenticationService.authenticate(payload) } returns Unit
		val controller = createController()

		val actual = controller.authenticate(TOKEN_PAIR)

		assertEquals(AuthenticationResult.Ok, actual)
		verify(exactly = 1) { tokenService.verifyAccessToken(ACCESS_TOKEN) }
		coVerify(exactly = 1) { authenticationService.authenticate(payload) }
	}

	private fun createController() = AuthenticationControllerImpl(
		authenticationService = authenticationService,
		tokenService = tokenService,
	)


	@ParameterizedTest
	@MethodSource("cases")
	fun `authenticate maps thrown exception to expected reason`(
		exception: Throwable,
		expectedReason: AuthenticationResult.Reason,
		thrownByTokenVerification: Boolean,
	) = runTest {
		val authenticationService: AuthenticationService = mockk()
		val tokenService: ITokenService = mockk()
		val payload = TokenPayload(subject = Uuid.NIL, username = USERNAME)
		val controller = AuthenticationControllerImpl(
			authenticationService = authenticationService,
			tokenService = tokenService,
		)

		if (thrownByTokenVerification) {
			every { tokenService.verifyAccessToken(ACCESS_TOKEN) } throws exception
		} else {
			every { tokenService.verifyAccessToken(ACCESS_TOKEN) } returns payload
			coEvery { authenticationService.authenticate(payload) } throws exception
		}

		val actual = controller.authenticate(TOKEN_PAIR)

		val error = assertIs<AuthenticationResult.Err>(actual)
		assertEquals(expectedReason, error.reason)
		verify(exactly = 1) { tokenService.verifyAccessToken(ACCESS_TOKEN) }
		if (thrownByTokenVerification) {
			coVerify(exactly = 0) { authenticationService.authenticate(any()) }
		} else {
			coVerify(exactly = 1) { authenticationService.authenticate(payload) }
		}
	}

	private companion object {
		const val ACCESS_TOKEN = "access-token"
		const val REFRESH_TOKEN = "refresh-token"
		const val USERNAME = "test-user"
		val TOKEN_PAIR = AuthenticationTokenPair(ACCESS_TOKEN, REFRESH_TOKEN)

		@JvmStatic
		fun cases(): Collection<Array<Any>> = listOf(
			arrayOf(mockk<AlgorithmMismatchException>(), AuthenticationResult.Reason.AlgorithmIncorrect, true),
			arrayOf(mockk<SignatureVerificationException>(), AuthenticationResult.Reason.SignatureIncorrect, true),
			arrayOf(mockk<TokenExpiredException>(), AuthenticationResult.Reason.Expired, true),
			arrayOf(mockk<MissingClaimException>(), AuthenticationResult.Reason.ClaimMissing, true),
			arrayOf(mockk<IncorrectClaimException>(), AuthenticationResult.Reason.ClaimIncorrect, true),
			arrayOf(mockk<JWTVerificationException>(), AuthenticationResult.Reason.Other, true),
			arrayOf(IllegalStateException(), AuthenticationResult.Reason.Other, true),
			arrayOf(AuthenticationException.AlreadyAuthenticated(), AuthenticationResult.Reason.AlreadyAuthenticated, false),
			arrayOf(AuthenticationException.InvalidCredentials(), AuthenticationResult.Reason.InvalidCredentials, false),
			arrayOf(AuthenticationException.InvalidRefreshToken(), AuthenticationResult.Reason.InvalidRefreshToken, false),
		)
	}
}
