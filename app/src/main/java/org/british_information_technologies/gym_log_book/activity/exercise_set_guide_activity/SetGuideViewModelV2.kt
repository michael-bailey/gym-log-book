package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.delegate.IExerciseTypeStateDelegate
import org.british_information_technologies.gym_log_book.delegate.impl.ExerciseTypeStateDelegate
import org.british_information_technologies.gym_log_book.lib.AppNotificationManager
import org.british_information_technologies.gym_log_book.lib.validation.Validator
import org.british_information_technologies.gym_log_book.repository.ExerciseEntryRepository
import org.british_information_technologies.gym_log_book.repository.ExerciseSetTimerRepository
import org.british_information_technologies.gym_log_book.repository.ExerciseTypeRepository
import javax.inject.Inject

@HiltViewModel
class SetGuideViewModelV2 @Inject constructor(
	exerciseTypeRepository: ExerciseTypeRepository,
	private val exerciseEntryRepository: ExerciseEntryRepository,
	private val notificationManager: AppNotificationManager,
	private val exerciseSetTimerRepository: ExerciseSetTimerRepository,
	private val savedStateHandle: SavedStateHandle,
) : ViewModel(),
	IExerciseTypeStateDelegate by ExerciseTypeStateDelegate(
		exerciseTypeRepository,
		CoroutineScope(Dispatchers.IO)
	),
	DefaultLifecycleObserver {

	// replacement for NavHost
	private val _pageState = MutableStateFlow(PageState.Start)
	val pageState = _pageState.asLiveData()

	// not quite sure about this one
	private var _isVisible: Boolean = false

	// field state
	private val currentExerciseSet = MutableStateFlow(1)
	private val currentWeight = MutableStateFlow("")
	private val currentReps = MutableStateFlow("")

	// exposing fields
	val exerciseSet = currentExerciseSet.asLiveData()
	val weight = currentWeight.asLiveData()
	val reps = currentReps.asLiveData()
	val timerValue = exerciseSetTimerRepository.timer.asLiveData()

	// start page state
	val isStartEnabled = currentExerciseType.map { it != null }.asLiveData()

	// add set state
	private val typeSetCombine = currentExerciseType
		.combine(currentExerciseSet) { t, s -> t to s }

	private val weightRepsCombine = currentWeight
		.combine(currentReps) { w, r -> w to r }

	val pastEntries =
		exerciseEntryRepository.exercises.combine(currentExerciseType) { entries, type ->
			entries.filter { entry ->
				entry.exerciseTypeId == type
			}.sortedBy { it.getLocalDateTime() }.takeLast(3)
		}.asLiveData()

	val canSubmit = typeSetCombine.combine(weightRepsCombine) { ts, wr ->
		val (type, set) = ts
		val (weight, reps) = wr

		when {
			type == null -> false
			set < 1 -> false
			Validator.FloatValidator().validator(weight).isFailure -> false
			Validator.NumberValidator().validator(reps).isFailure -> false
			else -> true
		}
	}.asLiveData()

	init {
		run {
			val keys = savedStateHandle.keys()
			if (keys.isEmpty()) {
				return@run
			}
			viewModelScope.launch {
				currentExerciseType.emit(savedStateHandle[ExerciseSetGuideActivityIntentUtils.EXERCISE_ID]!!)
				currentExerciseSet.emit(savedStateHandle[ExerciseSetGuideActivityIntentUtils.CURRENT_SET]!!)
				currentWeight.emit(savedStateHandle[ExerciseSetGuideActivityIntentUtils.CURRENT_WEIGHT]!!)
			}
		}
	}

	override fun onCreate(owner: LifecycleOwner) {

	}

	// Activity Lifecycle Events
	override fun onStart(owner: LifecycleOwner) {
		super.onStart(owner)
		if (!_isVisible) {
			notificationManager.cancelTimerNotification()
		}
		this._isVisible = true
	}

	override fun onStop(owner: LifecycleOwner) {
		super.onStop(owner)
		this._isVisible = false
	}

	// View Model Setters
	fun setWeight(weight: String) = viewModelScope.launch {
		currentWeight.emit(weight)
	}

	fun setReps(reps: String) = viewModelScope.launch {
		currentReps.emit(reps)
	}

	// timer stuff
	fun startTimer(onFinished: suspend () -> Unit) = viewModelScope.launch {

		val post = notificationManager.createTimerNotificationPoster(
			exercise = currentExerciseType.value!!,
			set = currentExerciseSet.value,
			weight = currentWeight.value.toDouble(),
		)

		exerciseSetTimerRepository.start(60) {
			viewModelScope.launch {
				if (!this@SetGuideViewModelV2._isVisible) {
					post()
				}
				onFinished()
			}
		}
	}

	fun resetTimer() = viewModelScope.launch {
		exerciseSetTimerRepository.stop()
	}

	/**
	 * gathers current state and saves it to the repository.
	 */
	fun saveSet() = viewModelScope.launch {

		val exerciseType = currentExerciseType.value
		val exerciseSet = currentExerciseSet.value
		val exerciseWeight = currentWeight.value
		val exerciseReps = currentReps.value

		if (canSubmit.value == null || canSubmit.value == false) return@launch

		exerciseEntryRepository.create(
			exercise = exerciseType!!,
			setNumber = exerciseSet,
			weight = exerciseWeight.toDouble(),
			reps = exerciseReps.toInt()
		)

		if (currentExerciseSet.value >= 3) {
			goToExtra()
		} else {
			goToPause()
			startTimer {
				goToSet()
			}
		}

		currentReps.emit("")
		currentExerciseSet.emit(currentExerciseSet.value + 1)
	}

	fun goToSet() =
		viewModelScope.launch { _pageState.emit(PageState.SetEntry) }

	fun goToPause() =
		viewModelScope.launch { _pageState.emit(PageState.Pause) }

	fun goToExtra() =
		viewModelScope.launch { _pageState.emit(PageState.ExtraSet) }
}