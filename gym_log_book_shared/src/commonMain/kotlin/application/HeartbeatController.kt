package net.michael_bailey.gym_log_book.shared.application

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.annotations.Rpc

@Rpc
interface HeartbeatController {
	fun heartbeat(): Flow<Long>
}