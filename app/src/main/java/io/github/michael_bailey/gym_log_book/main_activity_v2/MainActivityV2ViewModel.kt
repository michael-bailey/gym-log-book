package io.github.michael_bailey.gym_log_book.main_activity_v2

import androidx.lifecycle.AndroidViewModel
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem

class MainActivityV2ViewModel(application: App) : AndroidViewModel(
	application
) {
	private val _exerciseListState = application.exerciseTable.store
	val exerciseListState: List<ExerciseItem>
		get() = _exerciseListState
}