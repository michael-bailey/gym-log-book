package net.michael_bailey.gym_log_book.shared.authentication.controller

import kotlinx.rpc.annotations.Rpc
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

@Rpc
interface AuthenticationController {

	suspend fun getAuthenticationTokenPair(username: String, password: String): AuthenticationTokenPair
	suspend fun refreshAuthenticationTokenPair(authenticationTokenPair: AuthenticationTokenPair): AuthenticationTokenPair

}