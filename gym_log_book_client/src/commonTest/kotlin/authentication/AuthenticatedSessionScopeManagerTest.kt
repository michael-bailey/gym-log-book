package authentication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.client.authentication.AuthenticatedSessionScopeManager
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationRepository
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticatedSessionScopeManagerTest {

	@AfterTest
	fun tearDown() {
		stopKoin()
	}

	@Test
	fun `creates scope when auth state becomes authenticated and reuses it for repeated authenticated emissions`() =
		runTest {
			val repository = AuthenticationRepository()
			val service = AuthenticationService(repository, FakeAuthenticationController())
			val manager = createManager(
				authenticationService = service,
				testScope = CoroutineScope(StandardTestDispatcher(testScheduler)),
			)
			advanceUntilIdle()

			assertNull(manager.scope.value)

			repository.setToken(TOKEN_PAIR)
			advanceUntilIdle()
			val firstScope = manager.scope.value
			assertNotNull(firstScope)
			assertTrue(firstScope.isNotClosed())

			repository.setToken(SECOND_TOKEN_PAIR)
			advanceUntilIdle()

			assertSame(firstScope, manager.scope.value)
		}

	@Test
	fun `closes and clears scope when auth state becomes unauthenticated`() = runTest {
		val repository = AuthenticationRepository()
		val service = AuthenticationService(repository, FakeAuthenticationController())
		val manager = createManager(
			authenticationService = service,
			testScope = CoroutineScope(StandardTestDispatcher(testScheduler)),
		)
		advanceUntilIdle()

		repository.setToken(TOKEN_PAIR)
		advanceUntilIdle()
		val activeScope = manager.scope.value
		assertNotNull(activeScope)

		service.logout()
		advanceUntilIdle()

		assertNull(manager.scope.value)
		assertTrue(activeScope.closed)
	}

	private fun createManager(
		authenticationService: AuthenticationService,
		testScope: CoroutineScope,
	): AuthenticatedSessionScopeManager {
		val app = startKoin {
			modules(
				module {
					scope<AuthenticatedScope> {
						scoped { ScopeSentinel() }
					}
				}
			)
		}

		return AuthenticatedSessionScopeManager(
			authenticationService = authenticationService,
			appScope = testScope,
			koin = app.koin,
		)
	}

	private class FakeAuthenticationController : AuthenticationController {
		override suspend fun getAuthenticationTokenPair(
			username: String,
			password: String,
		): AuthenticationTokenPair = error("Not needed for this test")

		override suspend fun refreshAuthenticationTokenPair(
			authenticationTokenPair: AuthenticationTokenPair,
		): AuthenticationTokenPair = authenticationTokenPair
	}

	private class ScopeSentinel

	private companion object {
		val TOKEN_PAIR = AuthenticationTokenPair(
			accessToken = "access-token",
			refreshToken = "refresh-token",
		)
		val SECOND_TOKEN_PAIR = AuthenticationTokenPair(
			accessToken = "access-token-2",
			refreshToken = "refresh-token-2",
		)
	}
}
