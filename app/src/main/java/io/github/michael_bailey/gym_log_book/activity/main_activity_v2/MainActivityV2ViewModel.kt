package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.data_type.WeightItem

class MainActivityV2ViewModel(
	application: App,
	val exerciseListState: MediatorLiveData<List<ExerciseItem>> = MediatorLiveData(),
	val weightListState: MediatorLiveData<List<WeightItem>> = MediatorLiveData()
) : AndroidViewModel(
	application
) {
	fun forceUpdate() {
		getApplication<App>().exerciseDataManager.forceUpdate()
	}

	init {
		exerciseListState.addSource(
			application.exerciseDataManager.liveData
		) { t -> this@MainActivityV2ViewModel.exerciseListState.value = t }

		weightListState.addSource(
			application.weightDataManager.liveData
		) { t -> this@MainActivityV2ViewModel.weightListState.value = t }
	}
}