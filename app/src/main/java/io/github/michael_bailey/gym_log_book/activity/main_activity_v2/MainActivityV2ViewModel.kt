package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem

class MainActivityV2ViewModel(
	application: App,
	val exerciseListState: MediatorLiveData<List<ExerciseItem>> = MediatorLiveData()
) : AndroidViewModel(
	application
) {
	fun forceUpdate() {
		getApplication<App>().exerciseDataManager.forceUpdate()
	}

	init {
		exerciseListState.addSource(
			application.exerciseDataManager.liveData,
			object : Observer<List<ExerciseItem>> {
				override fun onChanged(t: List<ExerciseItem>?) {
					this@MainActivityV2ViewModel.exerciseListState.value = t
				}
			})
	}
}