package authentication.service

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.client.net.michael_bailey.gym_log_book.client.authentication.repository.IAuthenticationRepository
import net.michael_bailey.gym_log_book.shared.authentication.controller.NewAuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthenticationServiceTest {

	val isAuthenticatedFlow = MutableStateFlow(false)

	val authenticationRepository = mock<IAuthenticationRepository>()
	val newAuthenticationController = mock<NewAuthenticationController>()

	@BeforeTest
	fun setup() {
		every { newAuthenticationController.isAuthenticated() } returns isAuthenticatedFlow
	}

	@Test
	fun `Service forwards authentication status from controller`() = runTest {

		val underTest = AuthenticationService(
			newAuthenticationController = newAuthenticationController,
			authenticationRepository = authenticationRepository,
			coroutineScope = this.backgroundScope
		)

		underTest.isAuthenticated.test {
			assertEquals(false, awaitItem())
		}

		isAuthenticatedFlow.emit(true)

		underTest.isAuthenticated.test {
			assertEquals(true, awaitItem())
		}
	}

	@Test
	fun `Calling authenticate with valid token sets token in repository`() = runTest {

		everySuspend { newAuthenticationController.authenticate(testingToken) } returns AuthenticationResult.Ok
		everySuspend { authenticationRepository.setToken(testingToken) } returns Unit

		val underTest = AuthenticationService(
			newAuthenticationController = newAuthenticationController,
			authenticationRepository = authenticationRepository,
			coroutineScope = this.backgroundScope
		)

		underTest.authenticate(testingToken)

		verifySuspend {
			newAuthenticationController.authenticate(testingToken)
			authenticationRepository.setToken(testingToken)
		}
	}

	@Test
	fun `Calling authenticate with invalid tokens clears the token repository`() = runTest {
		val reason = AuthenticationResult.Reason.InvalidJwt
		everySuspend { newAuthenticationController.authenticate(testingToken) } returns AuthenticationResult.Err(
			reason = reason
		)
		everySuspend { authenticationRepository.clearToken() } returns Unit

		val underTest = AuthenticationService(
			newAuthenticationController = newAuthenticationController,
			authenticationRepository = authenticationRepository,
			coroutineScope = this.backgroundScope
		)

		underTest.authenticate(testingToken)

		verifySuspend {
			newAuthenticationController.authenticate(testingToken)
			authenticationRepository.clearToken()
		}

		underTest.recentErrors.test {
			assertEquals(reason, awaitItem())
		}
	}

	companion object {

		const val accessToken = "TESTING ACCESS	TOKEN"
		const val refreshToken = "TESTING REFRESH TOKEN"

		val testingToken = AuthenticationTokenPair(
			accessToken = accessToken,
			refreshToken = refreshToken
		)
	}

}