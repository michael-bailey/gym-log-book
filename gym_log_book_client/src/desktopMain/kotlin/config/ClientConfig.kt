package net.michael_bailey.gym_log_book.client.config

import io.ktor.http.*

actual class ClientConfig {

	private val environmentMap: Map<String, String>

	private val DEFAULT_PUBLIC_URL: Url = createUrl("rpc")
	private val DEFAULT_AUTHENTICATED_DESKTOP_URL: Url = createUrl("rpc", "authenticated")

	actual constructor() {
		this.environmentMap = System.getenv()
	}

	constructor(environmentMap: Map<String, String>) {
		this.environmentMap = environmentMap
	}

	actual val publicUrl: Url by lazy {
		this.environmentMap[PUBLIC_ENV_KEY]
			?.let(::parseUrl)
			?: DEFAULT_PUBLIC_URL
	}

	actual val authenticatedUrl: Url by lazy {
		environmentMap[AUTHENTICATED_ENV_KEY]
			?.let(::parseUrl)
			?: DEFAULT_AUTHENTICATED_DESKTOP_URL
	}

	private fun createUrl(
		vararg path: String
	): Url = buildUrl {
		protocol = URLProtocol.WS
		host = "localhost"
		port = 8080
		path(*path)
	}

	companion object {
		const val PUBLIC_ENV_KEY = "GYM_LOG_BOOK_PUBLIC_RPC_URL"
		const val AUTHENTICATED_ENV_KEY = "GYM_LOG_BOOK_AUTHENTICATED_RPC_URL"
	}
}
