package net.michael_bailey.gym_log_book.server.counter.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.server.counter.repository.CounterRepository
import org.koin.core.annotation.Factory

@Factory
class CounterService(
	private val counterRepository: CounterRepository,
) {
	fun observeCounter(): Flow<Int> = counterRepository.observeCounter()
}
