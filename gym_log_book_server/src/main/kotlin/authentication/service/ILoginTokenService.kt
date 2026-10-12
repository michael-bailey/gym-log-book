package net.michael_bailey.gym_log_book.server.authentication.service

import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

/**
 * An interface for creating and refreshing tokens
 *
 * This is being migrated to use exceptions to convey information
 *
 * Catch these exceptions at the controller level to convert them into nice state
 */
interface ILoginTokenService {

	fun createTokenPair(
		username: String,
		password: String
	): AuthenticationTokenPair

	fun refresh(tokenPair: AuthenticationTokenPair): AuthenticationTokenPair
}