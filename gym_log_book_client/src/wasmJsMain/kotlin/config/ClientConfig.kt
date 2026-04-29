package net.michael_bailey.gym_log_book.client.config

import io.ktor.http.*
import kotlinx.browser.window

actual class ClientConfig {

	actual val exerciseRpcUrl: String
		get() {
			val protocol = when (window.location.protocol) {
				"https:" -> "wss"
				else -> "ws"
			}

			val port = if (window.location.hostname == "localhost") "8080" else ""

			return "$protocol://${window.location.hostname}:$port/rpc"
		}

	actual val unauthenticatedUrl: Url
		get() = createUrl("rpc")
	actual val authenticationUrl: Url
		get() = createUrl("rpc", "authentication")
	actual val authenticatedUrl: Url
		get() = createUrl("rpc", "authenticated")

	private fun createUrl(
		vararg path: String
	): Url = buildUrl {

		host = window.location.hostname

		protocol = when (window.location.protocol) {
			"https:" -> URLProtocol.WSS
			else -> URLProtocol.WS
		}

		port = if (host == "localhost") 8080 else protocol.defaultPort

		path(*path)
	}
}