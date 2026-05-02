package net.michael_bailey.gym_log_book.client.authentication.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationRepository

class AuthenticationService(
	private val authenticationRepository: AuthenticationRepository,
) {

	private val _isAuthenticated = authenticationRepository.token.map { it != null }
	val isAuthenticated: Flow<Boolean> = _isAuthenticated

}