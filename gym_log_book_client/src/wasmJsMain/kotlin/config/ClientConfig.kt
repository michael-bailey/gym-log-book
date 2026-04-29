package net.michael_bailey.gym_log_book.client.config

import kotlinx.browser.window

actual object ClientConfig {
	actual val exerciseRpcUrl: String
		get() {
			val protocol = when (window.location.protocol) {
				"https:" -> "wss"
				else -> "ws"
			}

			val port = if (window.location.hostname == "localhost") "8080" else ""

			return "$protocol://${window.location.hostname}:$port/rpc"
		}
}