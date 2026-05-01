package net.michael_bailey.gym_log_book.client.authentication.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthenticationService {

	private val _isAuthenticated = MutableStateFlow(false)
	val isAuthenticated: Flow<Boolean> = _isAuthenticated

	suspend fun login() {
		_isAuthenticated.emit(true)
	}

	suspend fun logout() {
		_isAuthenticated.emit(false)
	}
}