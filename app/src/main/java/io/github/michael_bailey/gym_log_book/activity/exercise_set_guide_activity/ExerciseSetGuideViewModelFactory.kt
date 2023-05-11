package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.michael_bailey.gym_log_book.app.App

class ExerciseSetGuideViewModelFactory(
	private val application: App,
) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val a = SetGuideViewModel(application) as T
		return a
	}
}