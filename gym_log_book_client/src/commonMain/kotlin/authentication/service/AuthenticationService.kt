package net.michael_bailey.gym_log_book.client.authentication.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import net.michael_bailey.gym_log_book.client.authentication.repository.NewAuthenticationRepository
import net.michael_bailey.gym_log_book.client.net.michael_bailey.gym_log_book.client.authentication.repository.IAuthenticationRepository
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

class AuthenticationService(
	private val newAuthenticationRepository: NewAuthenticationRepository,
	private val authenticationRepository: IAuthenticationRepository,
	private val coroutineScope: CoroutineScope,
) {
	private val _recentErrors = MutableStateFlow<AuthenticationResult.Reason?>(null)

	val isAuthenticated: Flow<Boolean> = newAuthenticationRepository.isAuthenticated

	val recentErrors: Flow<AuthenticationResult.Reason?> = _recentErrors

	suspend fun authenticate(token: AuthenticationTokenPair) {
		when (val authResult = newAuthenticationRepository.authenticate(token).await()) {
			is AuthenticationResult.Ok -> handleAuthenticationSuccess(token)
			is AuthenticationResult.Err -> handleAuthenticationError(authResult)
		}
	}

	private suspend fun handleAuthenticationSuccess(token: AuthenticationTokenPair) {
		_recentErrors.emit(null)
		authenticationRepository.setToken(token)
	}

	private suspend fun handleAuthenticationError(
		authResult: AuthenticationResult.Err,
	) {
		authenticationRepository.clearToken()
		_recentErrors.emit(authResult.reason)
	}


}