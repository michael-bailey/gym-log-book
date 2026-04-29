package net.michael_bailey.gym_log_book.client.config

import io.ktor.http.*

expect class ClientConfig {

	constructor()

	val unauthenticatedUrl: Url
	val authenticationUrl: Url
	val authenticatedUrl: Url

	@Deprecated("Use authenticatedUrl instead")
	val exerciseRpcUrl: String

}
