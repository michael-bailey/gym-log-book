package net.michael_bailey.gym_log_book.server.authentication.factory

import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair
import org.koin.core.annotation.Single

@Single
class AuthenticatedTokenPairFactory {
	fun create(
		accessToken: String,
		refreshToken: String
	): AuthenticationTokenPair = AuthenticationTokenPair(
		accessToken = accessToken,
		refreshToken = refreshToken
	)
}