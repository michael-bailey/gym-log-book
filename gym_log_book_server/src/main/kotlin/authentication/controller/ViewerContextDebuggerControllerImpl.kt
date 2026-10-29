@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.controller

import net.michael_bailey.gym_log_book.server.di.RpcScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.ViewerContextDebuggerController
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Scoped
@Scope(RpcScope::class)
class ViewerContextDebuggerControllerImpl : ViewerContextDebuggerController {
	override suspend fun getUserId(): Uuid = Uuid.generateV7()
	override suspend fun getUserName(): String = "username"
}