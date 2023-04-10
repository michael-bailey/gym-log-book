package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.data_type.ExerciseType
import io.github.michael_bailey.gym_log_book.data_type.WeightItem

class MainActivityV2ViewModel(
	application: App,
	private val _exerciseTypeListState: MediatorLiveData<List<ExerciseType>> = MediatorLiveData(),
	private val _exerciseListState: MediatorLiveData<List<ExerciseItem>> = MediatorLiveData(),
	private val _weightListState: MediatorLiveData<List<WeightItem>> = MediatorLiveData(),
) : AndroidViewModel(
	application
) {

	val exerciseTypeListState: LiveData<List<ExerciseType>> get() = _exerciseTypeListState
	val exerciseListState: LiveData<List<ExerciseItem>> get() = _exerciseListState
	val weightListState: LiveData<List<WeightItem>> get() = _weightListState

	init {
		_exerciseTypeListState.apply {
			addSource(application.exerciseTypeDataManager.liveData) {
				this.value = it
			}
		}

		_exerciseListState.apply {
			addSource(application.exerciseDataManager.liveData) {
				this.value = it
			}
		}

		_weightListState.apply {
			addSource(application.weightDataManager.liveData) {
				this.value = it
			}
		}
	}

	fun deleteExercise(id: Int) {
		getApplication<App>().exerciseDataManager.delete(id)
	}

	fun deleteExerciseType(id: Int) {
		getApplication<App>().exerciseTypeDataManager.delete(id)
	}
}