package org.british_information_technologies.gym_log_book.activity.timer_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.extension.any.log
import org.british_information_technologies.gym_log_book.extension.view_model.launch
import org.british_information_technologies.gym_log_book.repository.ExerciseSetTimerRepository
import javax.inject.Inject

@HiltViewModel
class TimerActivityViewModel @Inject constructor(
	private val timerRepository: ExerciseSetTimerRepository
) : ViewModel() {

	val timer = timerRepository.timer.asLiveData()

	fun start() = launch {
		timerRepository.start(
			10
		) { log("TIMER IS DONE") }
	}

	fun stop() = launch {
		timerRepository.stop()
	}
}