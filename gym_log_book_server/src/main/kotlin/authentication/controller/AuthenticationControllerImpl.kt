@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.controller

import net.michael_bailey.gym_log_book.server.authentication.service.OldAuthenticationService
import net.michael_bailey.gym_log_book.server.di.RpcScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationException
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import kotlin.uuid.ExperimentalUuidApi

@Scoped
@Scope(RpcScope::class)
class AuthenticationControllerImpl(
	private val oldAuthenticationService: OldAuthenticationService,
) : AuthenticationController {

	init {
		println("OldAuthenticationControllerImpl created")
	}

	override suspend fun getAuthenticationTokenPair(
		username: String,
		password: String
	): AuthenticationTokenPair {
		println("--- getAuthenticationTokenPair ---")

		println("calling service")
		val token = oldAuthenticationService.createTokenPair(username, password)

		println("returning token: $token")
		return token ?: throw AuthenticationException.InvalidCredentials()

	}

	override suspend fun refreshAuthenticationTokenPair(authenticationTokenPair: AuthenticationTokenPair): AuthenticationTokenPair {
		return oldAuthenticationService.refresh(authenticationTokenPair)
			?: throw AuthenticationException.InvalidRefreshToken()
	}

}