@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.factory

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import net.michael_bailey.gym_log_book.server.authentication.config.JWTClaimNames.USERNAME_CLAIM_KEY
import net.michael_bailey.gym_log_book.server.authentication.model.ViewerContext
import net.michael_bailey.gym_log_book.server.authentication.scope.ViewerScope
import net.michael_bailey.gym_log_book.server.authentication.service.ITokenService
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Scoped
@Scope(ViewerScope::class)
class ViewerContextFactory(
	@InjectedParam private val call: ApplicationCall,
	private val tokenService: ITokenService
) {
	fun create(): ViewerContext {

		val jwtToken = call.principal<JWTPrincipal>() ?: return ViewerContext(
			userId = guestId,
			username = GUEST_USERNAME
		)

		val userId = jwtToken.payload.subject?.let { Uuid.parse(it) } ?: guestId
		val username = jwtToken.payload.claims[USERNAME_CLAIM_KEY]?.asString() ?: GUEST_USERNAME

		return ViewerContext(
			userId = userId,
			username = username
		)
	}

	companion object {
		val guestId = Uuid.NIL
		const val GUEST_USERNAME = "Guest"
	}
}