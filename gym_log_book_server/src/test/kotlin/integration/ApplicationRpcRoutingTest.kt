package integration

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.rpc.RpcClient
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.server.authentication.AuthenticationModule
import net.michael_bailey.gym_log_book.server.module
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ApplicationRpcRoutingTest {

	@AfterTest
	fun tearDown() {
		AuthenticationModule.environmentProvider = System::getenv
	}

	@Test
	fun `public rpc exposes authentication and counter controllers`() = testApplication {
		installTestEnvironment()
		application { module() }

		val client = createRpcClient()
		val publicRpc = client.publicRpc()

		val authController = publicRpc.withService<AuthenticationController>()
		val counterController = publicRpc.withService<CounterController>()

		val tokenPair = authController.getAuthenticationTokenPair(USERNAME, PASSWORD)
		val counterValue = counterController.observeCounter().first()

		assertTrue(tokenPair.accessToken.isNotBlank())
		assertTrue(tokenPair.refreshToken.isNotBlank())
		assertNotNull(counterValue)
	}

	@Test
	fun `authenticated rpc rejects unauthenticated clients and old auth path`() = testApplication {
		installTestEnvironment()
		application { module() }

		val client = createRpcClient()

		assertFails {
			client.authenticatedRpc().withService<ExerciseTypeController>().exerciseTypes().first()
		}

		assertFails {
			client.oldAuthenticationRpc().withService<AuthenticationController>()
				.getAuthenticationTokenPair(USERNAME, PASSWORD)
		}
	}

	@Test
	fun `authenticated rpc accepts valid bearer token`() = testApplication {
		installTestEnvironment()
		application { module() }

		val publicClient = createRpcClient()
		val tokenPair = publicClient.publicRpc()
			.withService<AuthenticationController>()
			.getAuthenticationTokenPair(USERNAME, PASSWORD)

		val authenticatedClient = createRpcClient(
			authorizationHeader = "Bearer ${tokenPair.accessToken}",
		)

		val types = authenticatedClient.authenticatedRpc()
			.withService<ExerciseTypeController>()
			.exerciseTypes()
			.first()

		assertNotNull(types)
	}

	private fun installTestEnvironment() {
		AuthenticationModule.environmentProvider = {
			mapOf(
				"JWT_SECRET" to JWT_SECRET,
				"USERNAME" to USERNAME,
				"PASSWORD" to PASSWORD,
			)
		}
	}

	private fun ApplicationTestBuilder.createRpcClient(
		authorizationHeader: String? = null,
	): HttpClient = createClient {
		installKrpc()
		if (authorizationHeader != null) {
			defaultRequest {
				header(HttpHeaders.Authorization, authorizationHeader)
			}
		}
	}

	private fun HttpClient.publicRpc(): RpcClient = rpc("/rpc") {
		rpcConfig {
			serialization { json() }
		}
	}

	private fun HttpClient.authenticatedRpc(): RpcClient = rpc("/rpc/authenticated") {
		rpcConfig {
			serialization { json() }
		}
	}

	private fun HttpClient.oldAuthenticationRpc(): RpcClient = rpc("/rpc/authentication") {
		rpcConfig {
			serialization { json() }
		}
	}

	private companion object {
		const val JWT_SECRET = "integration-jwt-secret"
		const val USERNAME = "integration-user"
		const val PASSWORD = "integration-password"
	}
}
