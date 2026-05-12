package net.michael_bailey.gym_log_book.client.authentication.service

import kotlinx.coroutines.withTimeout
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationRepository
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import kotlin.time.Duration.Companion.seconds

class AuthenticationLoginService(
	private val authenticationController: AuthenticationController,
	private val authenticationRepository: AuthenticationRepository,
) {

	suspend fun login(
		username: String,
		password: String
	) {
		val token = withTimeout(1.seconds) {
			authenticationController.getAuthenticationTokenPair(
				username = username,
				password = password
			)
		}
		authenticationRepository.setToken(token)
	}
}
