package net.michael_bailey.gym_log_book.server.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.shared.application.HeartbeatController
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.seconds

@Single
class HeartbeatControllerImpl(
	private val coroutineScope: CoroutineScope,
) : HeartbeatController {

	private val counter = MutableStateFlow(Long.MIN_VALUE)

	private val _heartbeatJob: Job = coroutineScope.launch {
		while (true) {
			delay(1.seconds)

			val count = counter.value

			if (count == Long.MAX_VALUE) counter.emit(Long.MIN_VALUE)
			else counter.emit(count + 1)
		}
	}

	override fun heartbeat(): Flow<Long> = counter
}
