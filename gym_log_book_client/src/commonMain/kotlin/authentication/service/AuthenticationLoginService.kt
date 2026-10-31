package net.michael_bailey.gym_log_book.client.authentication.service

import kotlinx.coroutines.withTimeout
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationStorageRepository
import net.michael_bailey.gym_log_book.client.authentication.repository.LoginRepository
import net.michael_bailey.gym_log_book.client.authentication.repository.NewAuthenticationRepository
import net.michael_bailey.gym_log_book.shared.authentication.exception.LoginTokenResult
import kotlin.time.Duration.Companion.seconds

class AuthenticationLoginService(
	private val loginRepository: LoginRepository,
	private val authenticationController: NewAuthenticationRepository,
	private val authenticationStorageRepository: AuthenticationStorageRepository,
) {

	suspend fun login(
		username: String,
		password: String
	) {

		println("Attempting login")
		val tokenRes = runCatching {
			val token = withTimeout(10.seconds) {
				println("in timeout")


				println("controller: $loginRepository, repo: $authenticationStorageRepository")
				println("username: $username, password: $password")

				loginRepository.getAuthenticationTokenPair(
					username = username,
					password = password
				)
			}
			println("Out timeout")

			token.await()
		}
		println("Out catching, result: $tokenRes")

		if (tokenRes.isFailure) {
			println("message ${tokenRes.exceptionOrNull()?.message}")
			return
		}

		println("setting token")
		when (val token = tokenRes.getOrThrow()) {
			is LoginTokenResult.Ok -> {
				// todo: replace this with better error handling
				authenticationController.authenticate(token.value).await()
				authenticationStorageRepository.setToken(token.value)
			}

			is LoginTokenResult.Err -> TODO()
		}


	}
}
