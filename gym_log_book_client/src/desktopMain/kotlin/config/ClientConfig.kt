package net.michael_bailey.gym_log_book.client.config

import io.ktor.http.*

actual class ClientConfig {

	private val environmentMap: Map<String, String>

	private val DEFAULT_DESKTOP_URL: Url = createUrl("rpc")
	private val DEFAULT_UNAUTHENTICATED_URL: Url = createUrl("rpc")
	private val DEFAULT_AUTHENTICATION_URL: Url = createUrl("rpc", "authentication")
	private val DEFAULT_AUTHENTICATED_DESKTOP_URL: Url = createUrl("rpc", "authenticated")

	actual constructor() {
		this.environmentMap = System.getenv()
	}

	constructor(environmentMap: Map<String, String>) {
		this.environmentMap = environmentMap
	}

	actual val exerciseRpcUrl: String =
		System.getenv("GYM_LOG_BOOK_RPC_URL")
			?.let(::parseUrl)?.toString()
			?: DEFAULT_DESKTOP_URL.toString()

	actual val unauthenticatedUrl: Url by lazy {
		this.environmentMap[UNAUTHENTICATED_ENV_KEY]
			?.let(::parseUrl)
			?: DEFAULT_UNAUTHENTICATED_URL
	}

	actual val authenticationUrl: Url by lazy {
		environmentMap[AUTHENTICATION_ENV_KEY]
			?.let(::parseUrl)
			?: DEFAULT_AUTHENTICATION_URL
	}

	actual val authenticatedUrl: Url by lazy {
		System.getenv(AUTHENTICATED_ENV_KEY)
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
		const val UNAUTHENTICATED_ENV_KEY = "GYM_LOG_BOOK_UNAUTHENTICATED_RPC_URL"
		const val AUTHENTICATION_ENV_KEY = "GYM_LOG_BOOK_AUTHENTICATION_RPC_URL"
		const val AUTHENTICATED_ENV_KEY = "GYM_LOG_BOOK_AUTHENTICATED_RPC_URL"
	}
}