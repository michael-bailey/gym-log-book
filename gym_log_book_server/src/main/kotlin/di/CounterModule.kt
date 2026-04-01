package net.michael_bailey.gym_log_book.server.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.michael_bailey.gym_log_book.server.counter.controller.CounterControllerImpl
import net.michael_bailey.gym_log_book.server.counter.repository.CounterRepository
import net.michael_bailey.gym_log_book.server.counter.service.CounterService
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.dsl.module

fun counterModule() = module {
	single { CounterRepository(CoroutineScope(Dispatchers.IO + SupervisorJob())) }
	single { CounterService(get()) }
	single { CounterControllerImpl(get()) }
	single<CounterController> { get<CounterControllerImpl>() }
}

@Module
@ComponentScan("net.michael_bailey.gym_log_book.server.counter")
@Configuration
class CounterModule {

	@Single
	fun coroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

}