package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.lib.ExerciseVerficationUtils
import java.time.LocalDate

/**
 * View model for guide activity
 */
class SetGuideViewModel(
	application: Application,

	// UI state
	private val _stringName: MutableLiveData<String> = MutableLiveData<String>(""),
	private val _stringWeight: MutableLiveData<String> = MutableLiveData<String>(""),
	private val _stringReps: MutableLiveData<String> = MutableLiveData<String>(""),

	// Internal state
	private val set: MutableLiveData<Int> = MutableLiveData<Int>(1),
) : AndroidViewModel(application) {

	// exposed state
	val exercise: LiveData<String> = _stringName
	val nextWeight: LiveData<String> = _stringWeight
	val nextReps: LiveData<String> = _stringReps
	val setNumber: LiveData<Int> = set

	val startEnabled = MediatorLiveData<Boolean>().apply {
		value = false
		addSource(exercise) {
			value = ExerciseVerficationUtils.verifyString(it).isSuccess
		}
	}

	val submitSetEnabled = MediatorLiveData<Boolean>().apply {
		initWeightAndReps(nextWeight, nextReps)
	}

	val weight = MediatorLiveData<Double>().apply {
		addSource(_stringWeight) {
			value = getWeight()
		}
	}

	private fun getWeight(): Double? {
		ExerciseVerficationUtils
			.verifyFloat(_stringWeight.value!!)
			.onSuccess {
				return@getWeight _stringWeight.value!!.toDouble()
			}
		return null
	}

	private fun getReps(): Int? {
		ExerciseVerficationUtils
			.verifyFloat(_stringReps.value!!)
			.onSuccess {
				return@getReps _stringReps.value!!.toInt()
			}
		return null
	}

	fun setReps(reps: String) {
		_stringReps.value = reps
	}

	fun setWeight(weight: String) {
		_stringWeight.postValue(weight)
	}

	fun updateCurrentExercise(exercise: String) {
		_stringName.value = exercise
	}

	fun completeSet() {
		set.value = set.value!!.plus(1)
		_stringReps.value = ""
	}

	fun finalise() {

		val a = exercise.value
		val b = setNumber.value
		val w = getWeight()
		val r = getReps()

		if (
			exercise.value == null ||
			setNumber.value == null ||
			w == null ||
			r == null
		) {
			return
		}

		getApplication<App>().exerciseDataManager.append {
			ExerciseItem(
				it,
				LocalDate.now(),
				exercise.value!!,
				setNumber.value!!,
				w,
				r,
			)
		}
	}
}

fun MediatorLiveData<Boolean>.initWeightAndReps(
	weight: LiveData<String>,
	reps: LiveData<String>,
	onVerification: (() -> Unit)? = null
) {
	value = false

	val check = { str: String ->
		value = ExerciseVerficationUtils.verifyFloat(weight.value!!).isSuccess &&
			ExerciseVerficationUtils.verifyFloat(reps.value!!).isSuccess
	}

	addSource(weight, check)
	addSource(reps, check)


}