package net.michael_bailey.gym_log_book.shared.authentication.exception

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthenticationResult {

	@Serializable
	data object Ok : AuthenticationResult()

	@Serializable
	class Err(
		val reason: Reason,
	) : AuthenticationResult()

	@Serializable
	enum class Reason(
		val message: String,
	) {
		InvalidJwt("Invalid Jwt provided"),
		Other("Other exception occurred"),
		ClaimIncorrect("Claim is incorrect"),
		ClaimMissing("Claim is missing"),
		Expired("Token is expired"),
		SignatureIncorrect("The signature is incorrect"),
		AlgorithmIncorrect("The algorithm is incorrect"),
		InvalidRefreshToken("The refresh token is incorrect"),
		InvalidCredentials("The credentials are incorrect"),
		AlreadyAuthenticated("Already authenticated");
	}
}