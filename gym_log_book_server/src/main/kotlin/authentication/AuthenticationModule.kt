package net.michael_bailey.gym_log_book.server.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.michael_bailey.gym_log_book.server.authentication.config.IJWTConfiguration
import net.michael_bailey.gym_log_book.server.authentication.factory.ViewerContextFactory
import net.michael_bailey.gym_log_book.server.authentication.model.ViewerContext
import net.michael_bailey.gym_log_book.server.authentication.scope.ViewerScope
import org.koin.core.annotation.*

@Module
@ComponentScan("net.michael_bailey.gym_log_book.server.authentication")
@Configuration
class AuthenticationModule {

	@Single
	fun jwtVerifier(
		authenticationConfiguration: IJWTConfiguration
	): JWTVerifier = JWT.require(Algorithm.HMAC256(authenticationConfiguration.jwtSecret))
		.withIssuer(authenticationConfiguration.issuer)
		.withAudience(authenticationConfiguration.audience)
		.build()

	@Single
	@Qualifier(Map::class, "environment")
	fun environmentMap(): Map<String, String> = environmentProvider()

	@Single
	fun coroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

	@Scoped
	@Scope(ViewerScope::class)
	fun viewerContext(factory: ViewerContextFactory): ViewerContext = factory.create()

	companion object {
		@Volatile
		var environmentProvider: () -> Map<String, String> = System::getenv
	}
}
