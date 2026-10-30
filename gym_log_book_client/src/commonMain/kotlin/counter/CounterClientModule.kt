package net.michael_bailey.gym_log_book.client.counter

import net.michael_bailey.gym_log_book.client.application.NetworkManager
import net.michael_bailey.gym_log_book.client.counter.repository.CounterRepository
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.client.counter.viewmodel.CounterViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val counterClientModule = module {

	single {
		val nm = get<NetworkManager>()
		CounterRepository(
			controller = nm.getService()
		)
	}

	singleOf(::CounterClientService)

	viewModelOf(::CounterViewModel)
}

