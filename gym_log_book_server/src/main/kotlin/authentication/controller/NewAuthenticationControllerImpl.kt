package net.michael_bailey.gym_log_book.server.authentication.controller

import com.auth0.jwt.exceptions.*
import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.server.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.server.authentication.service.ITokenService
import net.michael_bailey.gym_log_book.server.di.RpcScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.NewAuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationException
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Scoped
@Scope(
	RpcScope::class
)
class NewAuthenticationControllerImpl(
	val authenticationService: AuthenticationService,
	val tokenService: ITokenService,
) : NewAuthenticationController {

	override fun isAuthenticated(): Flow<Boolean> = authenticationService.isAuthenticated

	override suspend fun authenticate(token: AuthenticationTokenPair): AuthenticationResult = runCatching {
		val payload = tokenService.verifyAccessToken(token.accessToken)
		authenticationService.authenticate(payload)
	}.run(::convertToAuthenticationResult)

	private fun convertToAuthenticationResult(
		result: Result<Unit>,
	): AuthenticationResult = if (result.isSuccess) AuthenticationResult.Ok
	else handleError(result.exceptionOrNull()!!)

	private fun handleError(
		exception: Throwable,
	): AuthenticationResult = when (exception) {
		is JWTVerificationException -> handleError(exception = exception)
		is AuthenticationException -> handleError(exception = exception)
		else -> AuthenticationResult.Err(reason = AuthenticationResult.Reason.Other)
	}

	private fun handleError(exception: JWTVerificationException): AuthenticationResult = when (exception) {
		is AlgorithmMismatchException -> AuthenticationResult.Err(AuthenticationResult.Reason.AlgorithmIncorrect)
		is SignatureVerificationException -> AuthenticationResult.Err(AuthenticationResult.Reason.SignatureIncorrect)
		is TokenExpiredException -> AuthenticationResult.Err(AuthenticationResult.Reason.Expired)
		is MissingClaimException -> AuthenticationResult.Err(AuthenticationResult.Reason.ClaimMissing)
		is IncorrectClaimException -> AuthenticationResult.Err(AuthenticationResult.Reason.ClaimIncorrect)
		else -> AuthenticationResult.Err(AuthenticationResult.Reason.Other)
	}

	private fun handleError(
		exception: AuthenticationException,
	): AuthenticationResult = when (exception) {
		is AuthenticationException.AlreadyAuthenticated -> AuthenticationResult.Err(AuthenticationResult.Reason.AlreadyAuthenticated)
		is AuthenticationException.InvalidCredentials -> AuthenticationResult.Err(AuthenticationResult.Reason.InvalidCredentials)
		is AuthenticationException.InvalidRefreshToken -> AuthenticationResult.Err(AuthenticationResult.Reason.InvalidRefreshToken)
	}
}
