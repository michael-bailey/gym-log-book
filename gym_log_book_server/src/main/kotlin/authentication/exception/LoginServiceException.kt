package net.michael_bailey.gym_log_book.server.authentication.exception

sealed class LoginServiceException(message: String) : Exception(message) {
	class InvalidCredentials : LoginServiceException("Invalid username or password")
	class InvalidRefreshToken : LoginServiceException("Invalid or expired refresh token")
}