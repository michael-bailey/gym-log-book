package net.michael_bailey.gym_log_book.client.authentication.service

import kotlinx.coroutines.withTimeout
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationRepository
import net.michael_bailey.gym_log_book.shared.authentication.controller.OldAuthenticationController
import kotlin.time.Duration.Companion.seconds

class AuthenticationLoginService(
	private val oldAuthenticationController: OldAuthenticationController,
	private val authenticationRepository: AuthenticationRepository,
) {

	suspend fun login(
		username: String,
		password: String
	) {

		println("Attempting login")
		val token_res = runCatching {
			val token = withTimeout(10.seconds) {
				println("in timeout")


				println("controller: $oldAuthenticationController, repo: $authenticationRepository")
				println("username: $username, password: $password")

				oldAuthenticationController.getAuthenticationTokenPair(
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
		authenticationRepository.setToken(token_res.getOrNull()!!)
	}
}
