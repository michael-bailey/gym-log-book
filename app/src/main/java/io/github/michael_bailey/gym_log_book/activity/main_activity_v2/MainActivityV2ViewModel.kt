package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.data_type.ExerciseType
import io.github.michael_bailey.gym_log_book.data_type.WeightItem
import io.github.michael_bailey.gym_log_book.database.dao.ExerciseEntryDao
import io.github.michael_bailey.gym_log_book.database.dao.ExerciseTypeDao
import io.github.michael_bailey.gym_log_book.database.dao.WeightItemDao
import kotlinx.coroutines.launch

class MainActivityV2ViewModel(
	application: App,

	private val exerciseEntryDao: ExerciseEntryDao =
		application.db.exerciseEntryDao(),

	private val exerciseTypeDao: ExerciseTypeDao =
		application.db.exerciseTypeDao(),

	private val WeightEntryDao: WeightItemDao =
		application.db.WeightItemDao(),

	private val _exerciseTypeListState: MediatorLiveData<List<ExerciseType>> =
		MediatorLiveData<List<ExerciseType>>().apply {
			addSource(application.exerciseTypeDataManager.liveData) {
				this.value = it
			}
		},

	private val _exerciseListState: MediatorLiveData<List<ExerciseItem>> =
		MediatorLiveData<List<ExerciseItem>>().apply {
			addSource(application.exerciseDataManager.liveData) {
				this.value = it
			}
		},

	private val _weightListState: MediatorLiveData<List<WeightItem>> =
		MediatorLiveData<List<WeightItem>>().apply {
			addSource(application.weightDataManager.liveData) {
				this.value = it
			}
		},
) : AndroidViewModel(
	application
) {

	val exerciseTypeListState: LiveData<List<ExerciseType>> get() = _exerciseTypeListState
	val exerciseListState: LiveData<List<ExerciseItem>> get() = _exerciseListState
	val weightListState: LiveData<List<WeightItem>> get() = _weightListState

	val exerciseTypeList =
		viewModelScope.launch { exerciseTypeDao.queryAllExerciseTypes() }
	val exerciseEntryList = exerciseEntryDao.queryAllExercise()
	val weightEntryList = WeightEntryDao.queryAllWeight()

	fun deleteExercise(id: Int) {
		getApplication<App>().exerciseDataManager.delete(id)
	}

	fun deleteExerciseType(id: Int) {
		getApplication<App>().exerciseTypeDataManager.delete(id)
	}
}