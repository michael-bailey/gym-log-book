package net.michael_bailey.gym_log_book.client.config

import io.ktor.http.*

actual class ClientConfig {

	actual constructor()

	actual val publicUrl: Url = buildUrl {
		protocol = URLProtocol.WS
		host = "10.0.2.2"
		port = 8080
		path("rpc")
	}

	actual val authenticatedUrl: Url = buildUrl {
		protocol = URLProtocol.WS
		host = "10.0.2.2"
		port = 8080
		path("rpc", "authenticated")
	}
}
