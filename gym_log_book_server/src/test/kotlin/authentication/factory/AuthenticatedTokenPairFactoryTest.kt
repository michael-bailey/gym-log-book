package authentication.factory

import kotlinx.coroutines.test.runTest
import net.michael_bailey.gym_log_book.server.authentication.factory.AuthenticatedTokenPairFactory
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.junit.Test
import kotlin.test.assertEquals

class AuthenticatedTokenPairFactoryTest {

	@Test
	fun `test AuthenticatedTokenPairFactory returns new token pair`() = runTest {

		val actual = AuthenticationTokenPair(
			accessToken = ACCESS_TOKEN,
			refreshToken = REFRESH_TOKEN
		)

		val factory = AuthenticatedTokenPairFactory()
		val expected = factory.create(
			accessToken = ACCESS_TOKEN,
			refreshToken = REFRESH_TOKEN
		)

		assertEquals(expected = expected, actual = actual)

	}

	private companion object {
		const val ACCESS_TOKEN = "access token"
		const val REFRESH_TOKEN = "refresh token"
	}

}