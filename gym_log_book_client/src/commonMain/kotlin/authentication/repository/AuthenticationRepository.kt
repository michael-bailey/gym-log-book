package net.michael_bailey.gym_log_book.client.authentication.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

class AuthenticationRepository {

	private val _token = MutableStateFlow<AuthenticationTokenPair?>(null)
	val token: Flow<AuthenticationTokenPair?> = _token

	suspend fun setToken(token: AuthenticationTokenPair) {
		_token.emit(token)
	}

	suspend fun clearToken() {
		_token.emit(null)
	}


}