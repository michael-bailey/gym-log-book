@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.service

import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Single
class AuthenticationService(
	private val tokenService: ITokenService,
	private val usernamePasswordService: IUsernamePasswordService,
) {

	fun createTokenPair(
		username: String,
		password: String
	): AuthenticationTokenPair? {
		val isUsernamePasswordValid = usernamePasswordService.validateUsernameAndPassword(
			username = username,
			password = password,
		)
		return if (isUsernamePasswordValid) {
			val userId = Uuid.random()

			val accessToken = tokenService.issueAccessToken(
				userId = userId,
				username = username,
			)

			val refreshToken = tokenService.issueRefreshToken(
				userId = userId,
				username = username
			)

			AuthenticationTokenPair(
				accessToken = accessToken,
				refreshToken = refreshToken,
			)

		} else {
			null
		}
	}

	fun refresh(tokenPair: AuthenticationTokenPair): AuthenticationTokenPair? {
		val validatedPayload = tokenService
			.verifyRefreshToken(tokenPair.refreshToken) ?: return null

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