package net.michael_bailey.gym_log_book.shared.counter.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.annotations.Rpc

@Rpc
interface CounterController {
	fun observeCounter(): Flow<Int>
}
