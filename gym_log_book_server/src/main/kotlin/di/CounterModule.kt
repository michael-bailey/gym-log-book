package net.michael_bailey.gym_log_book.server.di

import kotlinx.coroutines.CoroutineScope
import net.michael_bailey.gym_log_book.server.counter.controller.CounterControllerImpl
import net.michael_bailey.gym_log_book.server.counter.repository.CounterRepository
import net.michael_bailey.gym_log_book.server.counter.service.CounterService
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import org.koin.dsl.module

fun counterModule(
	applicationScope: CoroutineScope,
) = module {
	single { CounterRepository(applicationScope) }
	single { CounterService(get()) }
	single { CounterControllerImpl(get()) }
	single<CounterController> { get<CounterControllerImpl>() }
}
