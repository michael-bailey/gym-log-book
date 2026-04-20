package net.michael_bailey.gym_log_book.server.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import kotlin.time.Clock

@Module
@Configuration
class ApplicationModule {
	@Single
	fun coroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

	@Single
	fun clock(): Clock = Clock.System
}