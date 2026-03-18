package net.michael_bailey.gym_log_book.client.counter.viewmodel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService

class CounterViewModel(
	private val counterClientService: CounterClientService,
	private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
) {
	private val counterText = MutableStateFlow("Loading...")

	init {
		scope.launch {
			counterClientService.observeCounter().collect { count ->
				counterText.update { count.toString() }
			}
		}
	}

	fun counterText(): StateFlow<String> = counterText.asStateFlow()

	fun close() {
		scope.cancel()
	}
}
