package net.michael_bailey.gym_log_book.client.config

actual object ClientConfig {
	private const val DEFAULT_DESKTOP_URL = "ws://localhost:8080/rpc"

	actual val exerciseRpcUrl: String =
		System.getenv("GYM_LOG_BOOK_RPC_URL")
			?.takeIf { value -> value.isNotBlank() }
			?: DEFAULT_DESKTOP_URL
}