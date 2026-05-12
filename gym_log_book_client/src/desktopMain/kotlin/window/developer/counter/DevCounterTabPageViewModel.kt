package net.michael_bailey.gym_log_book.client.window.developer.counter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService

class DevCounterTabPageViewModel(
	private val counterService: CounterClientService,
) : ViewModel() {

	val counterValue = counterService.observeCounter().map { it.toString() }

}