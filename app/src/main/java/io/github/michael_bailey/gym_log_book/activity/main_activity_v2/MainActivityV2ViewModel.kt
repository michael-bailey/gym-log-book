package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.data_type.WeightItem

class MainActivityV2ViewModel(
	application: App,
) : AndroidViewModel(
	application
) {
	val exerciseListState: LiveData<List<ExerciseItem>>
		get() =
			getApplication<App>().exerciseDataManager.liveData

	val weightListState: LiveData<List<WeightItem>>
		get() =
			getApplication<App>().weightDataManager.liveData

	fun forceUpdate() {
		getApplication<App>().exerciseDataManager.forceUpdate()
	}
}