@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.service

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

	fun verifyRefreshToken(token: String): TokenPayload?
}