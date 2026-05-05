package net.michael_bailey.gym_log_book.client.authentication.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationRepository
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

class AuthenticationService(
	private val authenticationRepository: AuthenticationRepository,
	private val authenticationController: AuthenticationController
) {

	val token: Flow<AuthenticationTokenPair?> = authenticationRepository.token

	private val _isAuthenticated = token.map { it != null }
	val isAuthenticated: Flow<Boolean> = _isAuthenticated

	suspend fun refreshTokens(): AuthenticationTokenPair? {
		val tokens = token.first() ?: return null
		val refreshedTokens = authenticationController.refreshAuthenticationTokenPair(tokens)
		authenticationRepository.setToken(refreshedTokens)
		return refreshedTokens
	}

	suspend fun logout() {
		authenticationRepository.clearToken()
	}

}
