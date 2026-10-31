package net.michael_bailey.gym_log_book.server.authentication.exception

sealed class TokenServiceException(
	message: String,
) : Exception(message) {
	class TokenTypeException : TokenServiceException("Token type is incorrect")
}