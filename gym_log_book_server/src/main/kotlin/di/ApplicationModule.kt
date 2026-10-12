@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.*
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Module
@Configuration
class ApplicationModule {
	@Single
	fun coroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

	@Single
	fun clock(): Clock = Clock.System

	@Factory
	fun rpcScope(
		@InjectedParam rpcSessionId: Uuid
	): RpcScope = RpcScope(rpcSessionId)
}