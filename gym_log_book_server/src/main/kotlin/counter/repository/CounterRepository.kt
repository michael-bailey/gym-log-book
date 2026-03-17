package net.michael_bailey.gym_log_book.server.counter.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class CounterRepository(
	private val scope: CoroutineScope,
	private val tickInterval: Duration = 1.seconds,
) {
	private val counter = MutableStateFlow(0)

	init {
		scope.launch {
			while (isActive) {
				delay(tickInterval)
				counter.update { value -> value + 1 }
			}
		}
	}

	fun observeCounter(): StateFlow<Int> = counter.asStateFlow()
}
