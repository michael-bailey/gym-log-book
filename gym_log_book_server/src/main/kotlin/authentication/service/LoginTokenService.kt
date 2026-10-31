@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.service

import net.michael_bailey.gym_log_book.server.authentication.exception.LoginServiceException
import net.michael_bailey.gym_log_book.server.di.RpcScope
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Scoped
@Scope(RpcScope::class)
class LoginTokenService(
	private val tokenService: ITokenService,
	private val usernamePasswordService: IUsernamePasswordService,
) : ILoginTokenService {
	override fun createTokenPair(
		username: String,
		password: String
	): AuthenticationTokenPair {
		val isUsernamePasswordValid = usernamePasswordService.validateUsernameAndPassword(
			username = username,
			password = password,
		)

		if (!isUsernamePasswordValid) throw LoginServiceException.InvalidCredentials()

		val userId = Uuid.random()

		val accessToken = tokenService.issueAccessToken(
			userId = userId,
			username = username,
		)

		val refreshToken = tokenService.issueRefreshToken(
			userId = userId,
			username = username
		)

		return AuthenticationTokenPair(
			accessToken = accessToken,
			refreshToken = refreshToken,
		)
	}

	override fun refresh(tokenPair: AuthenticationTokenPair): AuthenticationTokenPair {
		val validatedPayload = tokenService
			.verifyRefreshToken(tokenPair.refreshToken) ?: throw LoginServiceException.InvalidRefreshToken()

		val newAccessToken = tokenService.issueAccessToken(
			userId = validatedPayload.subject,
			username = validatedPayload.username
		)

		val newRefreshToken = tokenService.issueRefreshToken(
			userId = validatedPayload.subject,
			username = validatedPayload.username
		)

		return AuthenticationTokenPair(
			accessToken = newAccessToken,
			refreshToken = newRefreshToken,
		)
	}
}