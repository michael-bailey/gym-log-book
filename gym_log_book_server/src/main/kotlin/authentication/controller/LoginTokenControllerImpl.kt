package net.michael_bailey.gym_log_book.server.authentication.controller

import net.michael_bailey.gym_log_book.server.authentication.exception.LoginServiceException
import net.michael_bailey.gym_log_book.server.authentication.service.LoginTokenService
import net.michael_bailey.gym_log_book.server.di.RpcScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.LoginTokenController
import net.michael_bailey.gym_log_book.shared.authentication.exception.LoginTokenResult
import net.michael_bailey.gym_log_book.shared.authentication.exception.LoginTokenResult.Reason
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Scoped
@Scope(RpcScope::class)
class LoginTokenControllerImpl(
	private val loginTokenService: LoginTokenService
) : LoginTokenController {
	override suspend fun getAuthenticationTokenPair(
		username: String,
		password: String
	): LoginTokenResult = runCatching {
		loginTokenService.createTokenPair(
			username = username,
			password = password
		)
	}.run(::convertToLoginTokenResult)

	override suspend fun refreshAuthenticationTokenPair(
		loginTokenPair: AuthenticationTokenPair
	): LoginTokenResult = runCatching {
		loginTokenService.refresh(
			tokenPair = loginTokenPair
		)
	}.run(::convertToLoginTokenResult)

	private fun convertToLoginTokenResult(
		result: Result<AuthenticationTokenPair>
	): LoginTokenResult = if (result.isSuccess) {
		LoginTokenResult.Ok(result.getOrThrow())
	} else {
		LoginTokenResult.Err(
			reason = convertToReason(result.exceptionOrNull() as LoginServiceException)
		)
	}

	private fun convertToReason(
		exception: LoginServiceException
	): Reason = when (exception) {
		is LoginServiceException.InvalidCredentials -> Reason.InvalidCredential
		is LoginServiceException.InvalidRefreshToken -> Reason.RefreshTokenExpired
	}
}