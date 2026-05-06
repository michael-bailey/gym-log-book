@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.authentication.controller

import net.michael_bailey.gym_log_book.server.authentication.model.ViewerContext
import net.michael_bailey.gym_log_book.server.authentication.scope.ViewerScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.ViewerContextDebuggerController
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Scoped
@Scope(ViewerScope::class)
class ViewerContextDebuggerControllerImpl(
	private val viewerContext: ViewerContext
) : ViewerContextDebuggerController {
	override suspend fun getUserId(): Uuid = viewerContext.userId
	override suspend fun getUserName(): String = viewerContext.username
}