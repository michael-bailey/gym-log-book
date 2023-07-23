package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.gym_log_book.delegate.IExerciseTypeStateDelegate
import io.github.michael_bailey.gym_log_book.delegate.impl.ExerciseTypeStateDelegate
import io.github.michael_bailey.gym_log_book.lib.AppNotificationManager
import io.github.michael_bailey.gym_log_book.lib.validation.Validator
import io.github.michael_bailey.gym_log_book.repository.ExerciseEntryRepository
import io.github.michael_bailey.gym_log_book.repository.ExerciseTypeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SetGuideViewModelV2 @Inject constructor(
	exerciseTypeRepository: ExerciseTypeRepository,
	private val exerciseEntryRepository: ExerciseEntryRepository,
	private val notificationManager: AppNotificationManager,
	val savedStateHandle: SavedStateHandle
) : ViewModel(), IExerciseTypeStateDelegate by ExerciseTypeStateDelegate(
	exerciseTypeRepository,
	CoroutineScope(Dispatchers.IO)
) {


	// field state
	private var currentTimerValue = MutableStateFlow(60)
	private val currentExerciseSet = MutableStateFlow(1)
	private val currentWeight = MutableStateFlow("")
	private val currentReps = MutableStateFlow("")

	// exposing fields
	val exerciseSet = currentExerciseSet.asLiveData()
	val weight = currentWeight.asLiveData()
	val reps = currentReps.asLiveData()
	val timerValue = currentTimerValue.asLiveData()

	// start page state
	val isStartEnabled = currentExerciseType.map { it != null }.asLiveData()

	// add set state
	private val typeSetCombine = currentExerciseType
		.combine(currentExerciseSet) { t, s -> t to s }
	private val weightRepsCombine = currentWeight
		.combine(currentReps) { w, r -> w to r }

	// timer job
	var timerJob: Job? = null

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

	fun setWeight(weight: String) = viewModelScope.launch {
		currentWeight.emit(weight)
	}

	fun setReps(reps: String) = viewModelScope.launch {
		currentReps.emit(reps)
	}

	fun startTimer(onFinish: () -> Unit) {
		timerJob = viewModelScope.launch {
			while (currentTimerValue.value != 0) {
				delay(1.seconds)
				currentTimerValue.emit(currentTimerValue.value - 1)
			}
			notificationManager.postTimerNotification(
				exerciseType = currentExerciseType.value!!,
				set = currentExerciseSet.value,
				weight = currentWeight.value.toDouble(),
			)
			onFinish()
		}
	}

	fun resetTimer() = viewModelScope.launch {
		timerJob?.cancelAndJoin()
		currentTimerValue.emit(60)
		notificationManager.cancelTimerNotification()
	}

	/**
	 * gathers current state and saves it to the repository.
	 */
	fun finalise() = viewModelScope.launch {

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

		currentReps.emit("")
		currentExerciseSet.emit(currentExerciseSet.value + 1)
	}
}