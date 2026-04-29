package net.michael_bailey.gym_log_book.server.authentication.config

interface IJWTConfiguration {
	val accessExpiry: Int
	val refreshExpiry: Int
	val jwtSecret: String
	val issuer: String
	val audience: String
}