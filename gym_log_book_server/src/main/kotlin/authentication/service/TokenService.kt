@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import net.michael_bailey.gym_log_book.server.authentication.config.IJWTConfiguration
import net.michael_bailey.gym_log_book.server.authentication.config.JWTClaimNames.TYPE_CLAIM_KEY
import net.michael_bailey.gym_log_book.server.authentication.config.JWTClaimNames.USERNAME_CLAIM_KEY
import net.michael_bailey.gym_log_book.server.authentication.model.TokenPayload
import net.michael_bailey.gym_log_book.server.authentication.model.TokenType
import org.koin.core.annotation.Single
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Single
class TokenService(
	private val authenticationConfiguration: IJWTConfiguration,
) : ITokenService {

	override val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(authenticationConfiguration.jwtSecret))
		.withIssuer(authenticationConfiguration.issuer)
		.withAudience(authenticationConfiguration.audience)
		.build()

	override fun issueAccessToken(
		userId: Uuid,
		username: String,
	): String = JWT.create()
		.withIssuer(authenticationConfiguration.issuer)
		.withAudience(authenticationConfiguration.audience)
		.withSubject(userId.toString())
		.withClaim(TYPE_CLAIM_KEY, TokenType.Authentication.toString())
		.withClaim(USERNAME_CLAIM_KEY, username)
		.withExpiresAt(Date(System.currentTimeMillis() + authenticationConfiguration.accessExpiry))
		.sign(Algorithm.HMAC256(authenticationConfiguration.jwtSecret))

	override fun issueRefreshToken(
		userId: Uuid,
		username: String,
	): String = JWT.create()
		.withIssuer(authenticationConfiguration.issuer)
		.withAudience(authenticationConfiguration.audience)
		.withSubject(userId.toString())
		.withClaim(TYPE_CLAIM_KEY, TokenType.Refresh.toString())
		.withClaim(USERNAME_CLAIM_KEY, username)
		.withExpiresAt(Date(System.currentTimeMillis() + authenticationConfiguration.refreshExpiry))
		.sign(Algorithm.HMAC256(authenticationConfiguration.jwtSecret))

	override fun verifyRefreshToken(token: String): TokenPayload? = try {
		val decoded = verifier.verify(token)
		val type = decoded.getClaim(TYPE_CLAIM_KEY)
			.asString()

		if (TokenType.valueOf(type) != TokenType.Refresh) null
		else TokenPayload(
			subject = Uuid.parse(decoded.subject),
			username = decoded.claims[USERNAME_CLAIM_KEY]!!.asString()
		)
	} catch (e: JWTVerificationException) {
		return null
	}
}