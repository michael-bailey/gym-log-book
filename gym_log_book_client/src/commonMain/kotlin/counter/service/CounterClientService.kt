package net.michael_bailey.gym_log_book.client.counter.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.client.counter.repository.CounterRepository

class CounterClientService(
	private val counterRepository: CounterRepository,
) {
	val count: Flow<Int> = counterRepository.count
}
