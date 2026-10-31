package net.michael_bailey.gym_log_book.shared.authentication.exception

import kotlinx.serialization.Serializable
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

@Serializable
sealed class LoginTokenResult {

	@Serializable
	class Ok(
		val value: AuthenticationTokenPair,
	) : LoginTokenResult()

	@Serializable
	class Err(
		val reason: Reason,
	) : LoginTokenResult()

	@Serializable
	enum class Reason(
		val message: String
	) {
		InvalidCredential("Invalid username or password"),
		RefreshTokenExpired("Refresh token has expired, use username and password"),
		ServiceUnavailable("Another error occured")
	}
}

