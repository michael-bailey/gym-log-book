package io.github.michael_bailey.gym_log_book.repository

import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.gym_log_book.lib.AppNotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class ExerciseSetTimerRepository @OptIn(DelicateCoroutinesApi::class)
@Inject constructor(
	private val notificationManager: AppNotificationManager,
) {

	@OptIn(DelicateCoroutinesApi::class)
	private val scope: CoroutineScope = GlobalScope

	private val _timer = MutableStateFlow(0)

	val timer: Flow<Int> = _timer
	val isCountingDown = _timer.map { it > 0 }
	val isFinished = _timer.map { it > 0 }

	private var currentJob: Job? = null

	fun start(count: Int, onFinish: () -> Unit) {
		if (currentJob != null) {
			return
		}
		this.currentJob = scope.launch {
			if (count < 1) {
				return@launch
			}
			_timer.emit(count)
			do {
				delay(1.seconds)
				_timer.emit(timer.first() - 1)
				log("counting down")
			} while (timer.first() != 0)
			onFinish()
		}
	}

	suspend fun stop() {
		if (currentJob == null) {
			return
		}
		currentJob!!.cancelAndJoin()
		_timer.emit(0)
	}
}