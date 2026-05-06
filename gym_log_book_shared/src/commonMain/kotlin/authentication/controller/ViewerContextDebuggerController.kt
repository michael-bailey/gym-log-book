@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.shared.authentication.controller

import kotlinx.rpc.annotations.Rpc
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Rpc
interface ViewerContextDebuggerController {
	suspend fun getUserId(): Uuid
	suspend fun getUserName(): String
}