@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.service

import com.auth0.jwt.exceptions.JWTVerificationException
import net.michael_bailey.gym_log_book.server.authentication.model.TokenPayload
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ITokenService {
	fun issueAccessToken(
		userId: Uuid,
		username: String,
	): String

	fun issueRefreshToken(
		userId: Uuid,
		username: String
	): String

	/**
	 * @throws JWTVerificationException
	 * @throws com.auth0.jwt.exceptions.AlgorithmMismatchException – if the algorithm stated in the token's header is not equal to the one defined in the JWTVerifier.
	 * @throws com.auth0.jwt.exceptions.SignatureVerificationException – if the signature is invalid.
	 * @throws com.auth0.jwt.exceptions.TokenExpiredException – if the token has expired.
	 * @throws com.auth0.jwt.exceptions.MissingClaimException – if a claim to be verified is missing.
	 * @throws com.auth0.jwt.exceptions.IncorrectClaimException – if a claim contained a different value than the expected one.
	 */
	fun verifyAccessToken(accessToken: String): TokenPayload
	fun verifyRefreshToken(token: String): TokenPayload?
}