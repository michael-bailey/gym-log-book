package org.british_information_technologies.gym_log_book.repository

import dagger.hilt.android.scopes.ViewModelScoped
import io.github.michael_bailey.android_chat_kit.extension.any.log
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
import org.british_information_technologies.gym_log_book.lib.AppNotificationManager
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@ViewModelScoped
class ExerciseSetTimerRepository
@Inject constructor(
	private val notificationManager: AppNotificationManager,
) {

	@OptIn(DelicateCoroutinesApi::class)
	private val scope: CoroutineScope = GlobalScope

	private val _timer = MutableStateFlow(0)

	val timer: Flow<Int> = _timer
	val isCountingDown = _timer.map { isCounting() }
	val isFinished = _timer.map { isFinished() }

	private fun isCounting() = currentJob != null
	private fun isFinished() = currentJob == null

	private var currentJob: Job? = null

	/**
	 * Starts the pause timer
	 *
	 * If a timer already exists, then it won't start another.
	 *
	 * @author michael-bailey
	 */
	suspend fun start(count: Int, onFinish: () -> Unit) {
		val a = isCounting()
		log("is counting: $a")

		if (isCounting()) {
			return
		}
		_timer.emit(count)
		this.currentJob = scope.launch {
			do {
				delay(1.seconds)
				_timer.emit(timer.first() - 1)
				log("counting down")
			} while (timer.first() != 0)
			onFinish()
			currentJob = null
		}
		log("is counting: $a")
	}

	suspend fun stop() {
		if (currentJob == null) {
			return
		}
		currentJob!!.cancelAndJoin()
		currentJob = null
		_timer.emit(0)
	}
}