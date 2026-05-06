package net.michael_bailey.gym_log_book.shared.authentication.exception

sealed class AuthenticationException(message: String) : Exception(message) {
	class AlreadyAuthenticated : AuthenticationException("Viewer is already authenticated")
	class InvalidCredentials : AuthenticationException("Invalid username or password")
	class InvalidRefreshToken : AuthenticationException("Invalid or expired refresh token")
}
