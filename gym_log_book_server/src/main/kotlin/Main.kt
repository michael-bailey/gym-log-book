package net.michael_bailey.gym_log_book.server

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

private const val defaultPort = 8080
private const val defaultHost = "0.0.0.0"

fun main() {
	val port = System.getenv("PORT")?.toIntOrNull() ?: defaultPort
	val host = System.getenv("HOST")?.ifBlank { null } ?: defaultHost

	embeddedServer(
		factory = Netty,
		host = host,
		port = port,
		module = Application::module,
	).start(wait = true)
}
