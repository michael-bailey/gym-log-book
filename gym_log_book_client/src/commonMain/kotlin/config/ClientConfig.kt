package net.michael_bailey.gym_log_book.client.config

import io.ktor.http.*

expect class ClientConfig {

	constructor()

	val publicUrl: Url
	val authenticatedUrl: Url

}
