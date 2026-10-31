package net.michael_bailey.gym_log_book.client.net.michael_bailey.gym_log_book.client.authentication.repository

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

interface IAuthenticationRepository {
	val token: Flow<AuthenticationTokenPair?>

	suspend fun setToken(token: AuthenticationTokenPair)

	suspend fun clearToken()
}