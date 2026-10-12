@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.controller

import net.michael_bailey.gym_log_book.server.authentication.model.ViewerContext
import net.michael_bailey.gym_log_book.server.authentication.scope.ViewerScope
import net.michael_bailey.gym_log_book.server.authentication.service.OldAuthenticationService
import net.michael_bailey.gym_log_book.shared.authentication.controller.OldAuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationException
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Scoped
@Scope(ViewerScope::class)
class OldAuthenticationControllerImpl(
	private val viewerContext: ViewerContext,
	private val oldAuthenticationService: OldAuthenticationService,
) : OldAuthenticationController {

	override suspend fun getAuthenticationTokenPair(
		username: String,
		password: String
	): AuthenticationTokenPair {

		if (viewerContext.userId != Uuid.NIL)
			throw AuthenticationException.AlreadyAuthenticated()

		val token = oldAuthenticationService.createTokenPair(username, password)

		return token ?: throw AuthenticationException.InvalidCredentials()

	}

	override suspend fun refreshAuthenticationTokenPair(authenticationTokenPair: AuthenticationTokenPair): AuthenticationTokenPair {
		return oldAuthenticationService.refresh(authenticationTokenPair)
			?: throw AuthenticationException.InvalidRefreshToken()
	}

}