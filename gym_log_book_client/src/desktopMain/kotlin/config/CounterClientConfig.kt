package net.michael_bailey.gym_log_book.client.config

actual object CounterClientConfig {
	private const val defaultDesktopUrl = "ws://localhost:8080/rpc/counter"

	actual val counterRpcUrl: String =
		System.getenv("GYM_LOG_BOOK_RPC_URL")
			?.takeIf { value -> value.isNotBlank() }
			?: defaultDesktopUrl
}
