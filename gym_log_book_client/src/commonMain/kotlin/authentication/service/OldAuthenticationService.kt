package net.michael_bailey.gym_log_book.client.authentication.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationStorageRepository
import net.michael_bailey.gym_log_book.client.authentication.repository.LoginRepository
import net.michael_bailey.gym_log_book.client.authentication.repository.NewAuthenticationRepository
import net.michael_bailey.gym_log_book.shared.authentication.exception.LoginTokenResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

class OldAuthenticationService(
	private val authenticationStorageRepository: AuthenticationStorageRepository,
	private val authenticationController: NewAuthenticationRepository,
	private val loginRepository: LoginRepository,
) {

	val token: Flow<AuthenticationTokenPair?> = authenticationStorageRepository.token

	private val _isAuthenticated = token.map { it != null }
	val isAuthenticated: Flow<Boolean> = _isAuthenticated

	suspend fun refreshTokens(): AuthenticationTokenPair? {
		val tokens = token.first() ?: return null
		val refreshedTokens = loginRepository.refreshAuthenticationTokenPair(tokens).await()

		if (refreshedTokens is LoginTokenResult.Ok) {
			authenticationStorageRepository.setToken(refreshedTokens.value)
			authenticationController.authenticate(refreshedTokens.value).await()
			return refreshedTokens.value
		}

		return null
	}

	suspend fun logout() {
		authenticationStorageRepository.clearToken()
	}

}
