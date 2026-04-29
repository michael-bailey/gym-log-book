package net.michael_bailey.gym_log_book.server.authentication.config

import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds

@Single(
	binds = [IJWTConfiguration::class, IUsernamePasswordConfiguration::class]
)
class EnvironmentAuthenticationConfiguration(
	@Qualifier(Map::class, "environment") private val environmentMap: Map<String, String>,
) : IJWTConfiguration, IUsernamePasswordConfiguration {

	override val accessExpiry: Int = defaultAccessExpiry.inWholeSeconds.toInt()
	override val refreshExpiry: Int = defaultRefreshExpiry.inWholeSeconds.toInt()

	override val jwtSecret: String = environmentMap[JWT_SECRET_KEY]!!

	override val issuer: String = "gym.michael-bailey.net"
	override val audience: String = "gym.michael-bailey.net|clients"

	override val username: String = environmentMap[USERNAME_KEY]!!
	override val password: String = environmentMap[PASSWORD_KEY]!!

	companion object {
		const val JWT_SECRET_KEY = "JWT_SECRET"
		const val USERNAME_KEY = "USERNAME"
		const val PASSWORD_KEY = "PASSWORD"

		val defaultAccessExpiry: Duration = 3600.seconds
		val defaultRefreshExpiry: Duration = 30.days
	}
}