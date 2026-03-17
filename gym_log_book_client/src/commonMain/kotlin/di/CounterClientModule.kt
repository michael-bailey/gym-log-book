package net.michael_bailey.gym_log_book.client.di

import kotlinx.rpc.RpcClient
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import org.koin.core.module.Module
import org.koin.dsl.module

fun counterClientModule(
	rpcClient: RpcClient,
): Module = module {
	single<CounterController> {
		rpcClient.withService()
	}
	single { CounterClientService(get()) }
}
