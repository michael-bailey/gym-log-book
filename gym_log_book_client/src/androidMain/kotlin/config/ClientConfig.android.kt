package net.michael_bailey.gym_log_book.client.config

actual object ClientConfig {
	private const val DEFAULT_ANDROID_URL = "ws://10.0.2.2:8080/rpc"

	actual val exerciseRpcUrl: String = DEFAULT_ANDROID_URL
}
