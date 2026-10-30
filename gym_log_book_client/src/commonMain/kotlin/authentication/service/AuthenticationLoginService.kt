package net.michael_bailey.gym_log_book.client.authentication.service

import kotlinx.coroutines.withTimeout
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationStorageRepository
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import kotlin.time.Duration.Companion.seconds

class AuthenticationLoginService(
	private val authenticationController: AuthenticationController,
	private val authenticationStorageRepository: AuthenticationStorageRepository,
) {

	suspend fun login(
		username: String,
		password: String
	) {

		println("Attempting login")
		val token_res = runCatching {
			val token = withTimeout(10.seconds) {
				println("in timeout")


				println("controller: $authenticationController, repo: $authenticationStorageRepository")
				println("username: $username, password: $password")

				authenticationController.getAuthenticationTokenPair(
					username = username,
					password = password
				)
			}
			println("Out timeout")

			token
		}
		println("Out catching, result: $token_res")

		if (token_res.isFailure) {
			println("message ${token_res.exceptionOrNull()?.message}")
			return
		}

		println("setting token")
		authenticationStorageRepository.setToken(token_res.getOrNull()!!)
	}
}
