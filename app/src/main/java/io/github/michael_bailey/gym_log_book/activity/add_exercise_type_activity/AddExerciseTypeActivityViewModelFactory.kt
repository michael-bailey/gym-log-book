package io.github.michael_bailey.gym_log_book.activity.add_exercise_type_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.michael_bailey.gym_log_book.app.App

class AddExerciseTypeActivityViewModelFactory(
	private val application: App,
) : ViewModelProvider.NewInstanceFactory() {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val a = AddExerciseTypeActivityViewModel(application) as T
		return a
	}
}
