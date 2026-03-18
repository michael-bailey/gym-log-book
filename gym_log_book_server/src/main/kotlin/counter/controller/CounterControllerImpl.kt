package net.michael_bailey.gym_log_book.server.counter.controller

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.server.counter.service.CounterService
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController

class CounterControllerImpl(
	private val counterService: CounterService,
) : CounterController {
	override fun observeCounter(): Flow<Int> = counterService.observeCounter()
}
