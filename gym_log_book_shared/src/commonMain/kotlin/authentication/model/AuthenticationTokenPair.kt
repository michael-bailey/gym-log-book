package net.michael_bailey.gym_log_book.shared.authentication.model

data class AuthenticationTokenPair(
	val accessToken: String,
	val refreshToken: String,
)
