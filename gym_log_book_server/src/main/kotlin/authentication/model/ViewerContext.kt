@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ViewerContext(
	val userId: Uuid,
	val username: String,
)