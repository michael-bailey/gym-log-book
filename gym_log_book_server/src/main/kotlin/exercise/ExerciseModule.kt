package net.michael_bailey.gym_log_book.server.exercise

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("net.michael_bailey.gym_log_book.server.exercise")
@Configuration
class ExerciseModule {

	@Single
	fun coroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

}