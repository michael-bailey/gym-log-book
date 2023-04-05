package io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.lib.ExerciseVerficationUtils

/**
 * View model that contains the state, when modifying a exercise set
 */
class AmendExerciseViewModel(
	application: App,
	val exerciseId: Int?,

	// Field state
	private val _id: MutableLiveData<String> = MutableLiveData<String>(),
	private val _name: MutableLiveData<String> = MutableLiveData<String>(),
	private val _set: MutableLiveData<String> = MutableLiveData<String>(),
	private val _weight: MutableLiveData<String> = MutableLiveData<String>(),
	private val _reps: MutableLiveData<String> = MutableLiveData<String>(),

	// Error messages
	val nameError: MutableLiveData<String> =
		MediatorLiveData<String>()
			.apply {
				addSource(_name) { new ->
					ExerciseVerficationUtils.verifyExerciseName(
						new,
					).onFailure {
						this.value = it.message
					}.onSuccess {
						this.value = ""
					}
				}
			},

	val setError: MutableLiveData<String> =
		MediatorLiveData<String>()
			.apply {
				addSource(_set) { new ->
					ExerciseVerficationUtils.verifySet(
						new,
					).onFailure {
						this.value = it.message
					}.onSuccess {
						this.value = ""
					}
				}
			},

	val weightError: MutableLiveData<String> =
		MediatorLiveData<String>()
			.apply {
				addSource(_weight) { new ->
					ExerciseVerficationUtils.verifyWeight(
						new,
					).onFailure {
						this.value = it.message
					}.onSuccess {
						this.value = ""
					}
				}
			},

	val repsError: MutableLiveData<String> =
		MediatorLiveData<String>()
			.apply {
				addSource(_reps) { new ->
					ExerciseVerficationUtils.verifyReps(
						new,
					).onFailure {
						this.value = it.message
					}.onSuccess {
						this.value = ""
					}
				}
			},

	) : AndroidViewModel(
	application
) {

	// expose non-mutable live data Fields
	val id: LiveData<String> get() = _id
	val exerciseName: LiveData<String> get() = _name
	val set: LiveData<String> get() = _set
	val weight: LiveData<String> get() = _weight
	val reps: LiveData<String> get() = _reps

	val item =
		kotlin.runCatching {
			getApplication<App>().exerciseDataManager.liveData.value?.first { it.id == exerciseId }
		}.getOrNull()

	init {
		item?.let {
			_name.value = it.exercise
			_set.value = it.setNumber.toString()
			_weight.value = it.weight.toString()
			_reps.value = it.reps.toString()
		}
		if (item == null) {
			_name.value = ""
			_set.value = ""
			_weight.value = ""
			_reps.value = ""
		}
	}

	fun setName(new: String) {
		_name.value = new
	}

	fun setSet(new: String) {
		_set.value = new
	}

	fun setWeight(new: String) {
		_weight.value = new
	}

	fun setReps(new: String) {
		_reps.value = new
	}

	fun finalise() {
		getApplication<App>().exerciseDataManager.apply {
			exerciseId?.let {
				update(it) {
					this.exercise =
						this@AmendExerciseViewModel.exerciseName.value ?: this.exercise
					this.setNumber =
						this@AmendExerciseViewModel.set.value?.toInt() ?: this.setNumber
					this.weight =
						this@AmendExerciseViewModel.weight.value?.toDouble() ?: this.weight
					this.reps =
						this@AmendExerciseViewModel.reps.value?.toInt() ?: this.reps
				}
			}
		}
	}
}