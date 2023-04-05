package io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.michael_bailey.gym_log_book.App

class AmendExerciseViewModelFactory(
	private val application: App,
	private val exerciseId: Int?
) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val a = AmendExerciseViewModel(application, exerciseId) as T
		return a
	}
}