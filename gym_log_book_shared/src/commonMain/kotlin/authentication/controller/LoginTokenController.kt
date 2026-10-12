package net.michael_bailey.gym_log_book.shared.authentication.controller

import kotlinx.rpc.annotations.Rpc
import net.michael_bailey.gym_log_book.shared.authentication.exception.LoginTokenResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

@Rpc
interface LoginTokenController {

	suspend fun getAuthenticationTokenPair(username: String, password: String): LoginTokenResult
	suspend fun refreshAuthenticationTokenPair(loginTokenPair: AuthenticationTokenPair): LoginTokenResult

}