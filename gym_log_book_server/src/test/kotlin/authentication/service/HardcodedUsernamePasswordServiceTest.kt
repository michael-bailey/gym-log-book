package authentication.service

import net.michael_bailey.gym_log_book.server.authentication.config.IUsernamePasswordConfiguration
import net.michael_bailey.gym_log_book.server.authentication.service.HardcodedUsernamePasswordService
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HardcodedUsernamePasswordServiceTest {

	private val configuration: IUsernamePasswordConfiguration = object : IUsernamePasswordConfiguration {
		override val username: String = USERNAME
		override val password: String = PASSWORD
	}

	private val service = HardcodedUsernamePasswordService(configuration)

	@Test
	fun `test validateUsernameAndPassword with matching credentials returns true`() {
		val isValid = service.validateUsernameAndPassword(
			username = USERNAME,
			password = PASSWORD
		)

		assertTrue(isValid)
	}

	@Test
	fun `test validateUsernameAndPassword with wrong username returns false`() {
		val isValid = service.validateUsernameAndPassword(
			username = "wrong-username",
			password = PASSWORD
		)

		assertFalse(isValid)
	}

	@Test
	fun `test validateUsernameAndPassword with wrong password returns false`() {
		val isValid = service.validateUsernameAndPassword(
			username = USERNAME,
			password = "wrong-password"
		)

		assertFalse(isValid)
	}

	private companion object {
		const val USERNAME = "test-user"
		const val PASSWORD = "test-password"
	}

}