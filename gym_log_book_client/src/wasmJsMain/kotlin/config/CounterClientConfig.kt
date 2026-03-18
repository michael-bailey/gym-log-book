package net.michael_bailey.gym_log_book.client.config

import kotlinx.browser.window

actual object CounterClientConfig {
	actual val counterRpcUrl: String
		get() {
			val protocol = when (window.location.protocol) {
				"https:" -> "wss"
				else -> "ws"
			}

			return "$protocol://${window.location.host}/rpc/counter"
		}
}
