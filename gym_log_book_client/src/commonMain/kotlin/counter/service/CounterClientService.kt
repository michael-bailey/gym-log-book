package net.michael_bailey.gym_log_book.client.counter.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController

class CounterClientService(
	private val counterController: CounterController,
) {
	fun observeCounter(): Flow<Int> = counterController.observeCounter()
}
