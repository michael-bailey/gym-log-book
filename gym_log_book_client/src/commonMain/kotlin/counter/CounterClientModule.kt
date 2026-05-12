package net.michael_bailey.gym_log_book.client.counter

import kotlinx.rpc.RpcClient
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.client.counter.viewmodel.CounterViewModel
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val counterClientModule = module {

single {
		println("creating counter RCP")
		get<RpcClient>().withService<CounterController>()
	} bind CounterController::class

	single {

		val controller = get<CounterController>()

		CounterClientService(
			counterController = controller
		)
	}

	viewModelOf(::CounterViewModel)
}

