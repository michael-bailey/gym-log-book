package net.michael_bailey.gym_log_book.shared.authentication.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.annotations.Rpc
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

@Rpc
interface AuthenticationController {

	fun isAuthenticated(): Flow<Boolean>

	suspend fun authenticate(token: AuthenticationTokenPair): AuthenticationResult

}