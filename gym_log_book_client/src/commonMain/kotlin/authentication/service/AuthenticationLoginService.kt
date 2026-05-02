package net.michael_bailey.gym_log_book.client.authentication.service

import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationRepository
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController

class AuthenticationLoginService(
	private val authenticationController: AuthenticationController,
	private val authenticationRepository: AuthenticationRepository,
) {

	suspend fun getAuthenticationTokenPair(username: String, password: String) =
		authenticationController.getAuthenticationTokenPair(username, password)

	suspend fun login(
		username: String,
		password: String
	) {
		val token = authenticationController.getAuthenticationTokenPair(
			username = username,
			password = password
		)
		authenticationRepository.setToken(token)
	}

	suspend fun logout() {
		authenticationRepository.clearToken()
	}
}