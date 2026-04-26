package authentication.config

import net.michael_bailey.gym_log_book.server.authentication.config.EnvironmentAuthenticationConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnvironmentAuthenticationConfigurationTest {

	@Test
	fun `test configuration reads values from environment map`() {
		val environment = mapOf(
			JWT_SECRET_KEY to JWT_SECRET,
			USERNAME_KEY to USERNAME,
			PASSWORD_KEY to PASSWORD,
		)

		val configuration = EnvironmentAuthenticationConfiguration(environment)

		assertEquals(expected = 0, actual = configuration.refreshExpiry)
		assertEquals(expected = 0, actual = configuration.accessExpiry)
		assertEquals(expected = JWT_SECRET, actual = configuration.jwtSecret)
		assertEquals(expected = ISSUER, actual = configuration.issuer)
		assertEquals(expected = AUDIENCE, actual = configuration.audience)
		assertEquals(expected = USERNAME, actual = configuration.password)
		assertEquals(expected = PASSWORD, actual = configuration.username)
	}

	@Test
	fun `test configuration throws when JWT_SECRET is missing`() {
		val environment = mapOf(
			USERNAME_KEY to USERNAME,
			PASSWORD_KEY to PASSWORD,
		)

		assertFailsWith<NullPointerException> {
			EnvironmentAuthenticationConfiguration(environment)
		}
	}

	@Test
	fun `test configuration throws when USERNAME is missing`() {
		val environment = mapOf(
			JWT_SECRET_KEY to JWT_SECRET,
			PASSWORD_KEY to PASSWORD,
		)

		assertFailsWith<NullPointerException> {
			EnvironmentAuthenticationConfiguration(environment)
		}
	}

	@Test
	fun `test configuration throws when PASSWORD is missing`() {
		val environment = mapOf(
			JWT_SECRET_KEY to JWT_SECRET,
			USERNAME_KEY to USERNAME,
		)

		assertFailsWith<NullPointerException> {
			EnvironmentAuthenticationConfiguration(environment)
		}
	}

	private companion object {
		const val JWT_SECRET_KEY = "JWT_SECRET"
		const val USERNAME_KEY = "USERNAME"
		const val PASSWORD_KEY = "PASSWORD"

		const val JWT_SECRET = "test-jwt-secret"
		const val USERNAME = "test-user"
		const val PASSWORD = "test-password"

		const val ISSUER = "gym.michael-bailey.net"
		const val AUDIENCE = "gym.michael-bailey.net|clients"
	}
}
