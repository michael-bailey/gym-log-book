package net.michael_bailey.gym_log_book.shared.authentication.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationTokenPair(
	val accessToken: String,
	val refreshToken: String,
)
