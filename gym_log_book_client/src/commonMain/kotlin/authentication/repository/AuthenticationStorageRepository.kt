package net.michael_bailey.gym_log_book.client.authentication.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import net.michael_bailey.gym_log_book.client.net.michael_bailey.gym_log_book.client.authentication.repository.IAuthenticationRepository
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

class AuthenticationStorageRepository : IAuthenticationRepository {

	private val _token = MutableStateFlow<AuthenticationTokenPair?>(null)
	override val token: Flow<AuthenticationTokenPair?> = _token

	override suspend fun setToken(token: AuthenticationTokenPair) {
		_token.emit(token)
	}

	override suspend fun clearToken() {
		_token.emit(null)
	}
}