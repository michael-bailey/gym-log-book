@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.di

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


class RpcScope(
	val rpcSessionId: Uuid
) : KoinScopeComponent {
	override val scope: Scope = createScope(this)

	inline fun <reified T> declare(
		instance: T,
		qualifier: Qualifier? = null,
		secondaryTypes: List<KClass<*>> = emptyList(),
		allowOverride: Boolean = true,
		holdInstance: Boolean = false
	) = scope.declare(
		instance = instance,
		qualifier = qualifier,
		secondaryTypes = secondaryTypes,
		allowOverride = allowOverride,
		holdInstance = holdInstance
	)

	fun close() = scope.close()
}