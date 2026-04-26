package net.michael_bailey.gym_log_book.server.authentication.config

import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single

@Single(
	binds = [IJWTConfiguration::class, IUsernamePasswordConfiguration::class]
)
class EnvironmentAuthenticationConfiguration(
	@Qualifier(Map::class, "environment") private val environmentMap: Map<String, String>,
) : IJWTConfiguration, IUsernamePasswordConfiguration {

	override val refreshExpiry: Int = 0
	override val accessExpiry: Int = 0
	override val jwtSecret: String = environmentMap[JWT_SECRET_KEY]!!

	override val issuer: String = "gym.michael-bailey.net"
	override val audience: String = "gym.michael-bailey.net|clients"

	override val password: String = environmentMap[USERNAME_KEY]!!
	override val username: String = environmentMap[PASSWORD_KEY]!!

	private companion object {
		const val JWT_SECRET_KEY = "JWT_SECRET"
		const val USERNAME_KEY = "USERNAME"
		const val PASSWORD_KEY = "PASSWORD"
	}
}