@file:OptIn(ExperimentalUuidApi::class)

package authentication.service

import net.michael_bailey.gym_log_book.server.authentication.config.IJWTConfiguration
import net.michael_bailey.gym_log_book.server.authentication.config.JWTClaimNames.TYPE_CLAIM_KEY
import net.michael_bailey.gym_log_book.server.authentication.config.JWTClaimNames.USERNAME_CLAIM_KEY
import net.michael_bailey.gym_log_book.server.authentication.model.TokenPayload
import net.michael_bailey.gym_log_book.server.authentication.model.TokenType
import net.michael_bailey.gym_log_book.server.authentication.service.TokenService
import kotlin.test.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TokenServiceTest {

	private val configuration: IJWTConfiguration = object : IJWTConfiguration {
		override val refreshExpiry: Int = 120_000
		override val accessExpiry: Int = 60_000
		override val jwtSecret: String = "test-secret"
		override val issuer: String = "test-issuer"
		override val audience: String = "test-audience"
	}

	private val service = TokenService(configuration)

	@Test
	fun `test issueAccessToken creates signed token with expected claims`() {
		val now = System.currentTimeMillis()
		val token = service.issueAccessToken(
			userId = userId,
			username = USERNAME
		)

		val decoded = service.verifier.verify(token)

		assertEquals(expected = configuration.issuer, actual = decoded.issuer)
		assertEquals(expected = configuration.audience, actual = decoded.audience.single())
		assertEquals(expected = userId.toString(), actual = decoded.subject)
		assertEquals(expected = TokenType.Authentication.toString(), actual = decoded.getClaim(TYPE_CLAIM_KEY).asString())
		assertEquals(expected = USERNAME, actual = decoded.getClaim(USERNAME_CLAIM_KEY).asString())

		val expiration = decoded.expiresAt?.time
		assertNotNull(expiration)
		assertTrue(actual = expiration > now)
		assertTrue(actual = expiration <= now + configuration.accessExpiry + CLOCK_SKEW_TOLERANCE_MS)
	}

	@Test
	fun `test issueRefreshToken creates signed token with expected claims`() {
		val now = System.currentTimeMillis()
		val token = service.issueRefreshToken(
			userId = userId,
			username = USERNAME
		)

		val decoded = service.verifier.verify(token)

		assertEquals(expected = configuration.issuer, actual = decoded.issuer)
		assertEquals(expected = configuration.audience, actual = decoded.audience.single())
		assertEquals(expected = userId.toString(), actual = decoded.subject)
		assertEquals(expected = TokenType.Refresh.toString(), actual = decoded.getClaim(TYPE_CLAIM_KEY).asString())
		assertEquals(expected = USERNAME, actual = decoded.getClaim(USERNAME_CLAIM_KEY).asString())

		val expiration = decoded.expiresAt?.time
		assertNotNull(expiration)
		assertTrue(actual = expiration > now)
		assertTrue(actual = expiration <= now + configuration.refreshExpiry + CLOCK_SKEW_TOLERANCE_MS)
	}

	@Test
	fun `test verifyRefreshToken with refresh token returns payload`() {
		val token = service.issueRefreshToken(
			userId = userId,
			username = USERNAME
		)

		val expected = TokenPayload(
			subject = userId,
			username = USERNAME
		)

		val actual = service.verifyRefreshToken(token)

		assertEquals(expected = expected, actual = actual)
	}

	@Test
	fun `test verifyRefreshToken with access token returns null`() {
		val token = service.issueAccessToken(
			userId = userId,
			username = USERNAME
		)

		val actual = service.verifyRefreshToken(token)

		assertNull(actual)
	}

	@Test
	fun `test verifyRefreshToken with invalid token returns null`() {
		val actual = service.verifyRefreshToken("invalid-token")

		assertNull(actual)
	}

	private companion object {
		const val USERNAME = "username"
		const val CLOCK_SKEW_TOLERANCE_MS = 2_000
		val userId: Uuid = Uuid.random()
	}
}
