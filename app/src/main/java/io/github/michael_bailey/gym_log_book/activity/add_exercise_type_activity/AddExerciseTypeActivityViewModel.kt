package io.github.michael_bailey.gym_log_book.activity.add_exercise_type_activity

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseType

class AddExerciseTypeActivityViewModel(
	application: App,

	private val _exerciseName: MutableLiveData<String> = MutableLiveData(""),
	private val _isUsingUserWeight: MutableLiveData<Boolean> = MutableLiveData(
		false
	)

) : AndroidViewModel(
	application
) {

	val exerciseName get() = _exerciseName
	val isUsingUserWeight get() = _isUsingUserWeight

	fun setExerciseName(value: String) {
		_exerciseName.value = value
	}

	fun setIsUsingUserWeight(value: Boolean) {
		_isUsingUserWeight.value = value
	}

	fun finalise() {
		getApplication<App>().exerciseTypeDataManager.append {
			ExerciseType(
				it,
				exerciseName.value!!,
				isUsingUserWeight.value!!
			)
		}
	}
}