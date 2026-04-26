package net.michael_bailey.gym_log_book.server.authentication.service

import net.michael_bailey.gym_log_book.server.authentication.config.IUsernamePasswordConfiguration
import org.koin.core.annotation.Single

@Single
class HardcodedUsernamePasswordService(
	private val usernamePasswordConfiguration: IUsernamePasswordConfiguration
) : IUsernamePasswordService {
	override fun validateUsernameAndPassword(username: String, password: String): Boolean =
		username == usernamePasswordConfiguration.username &&
				password == usernamePasswordConfiguration.password
}