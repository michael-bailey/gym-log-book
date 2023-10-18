package org.british_information_technologies.gym_log_book.activity.amend_exercise_activity_v2

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.activity.amend_exercise_activity_v2.AmendExerciseActivityV2IntentUtils.INTENT_ID
import org.british_information_technologies.gym_log_book.delegate.IExerciseTypeStateDelegate
import org.british_information_technologies.gym_log_book.delegate.impl.ExerciseTypeStateDelegate
import org.british_information_technologies.gym_log_book.repository.ExerciseEntryRepository
import org.british_information_technologies.gym_log_book.repository.ExerciseTypeRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AmendExerciseViewModelV2 @Inject constructor(
	private val exerciseRepository: ExerciseEntryRepository,
	private val exerciseTypeRepository: ExerciseTypeRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel(), IExerciseTypeStateDelegate by ExerciseTypeStateDelegate(
	exerciseTypeRepository,
	CoroutineScope(Dispatchers.IO)
) {

	// internal state
	private val exerciseID = savedStateHandle.get<UUID>(INTENT_ID)
	private val currentExerciseValue =
		flow { exerciseID?.let { emit(exerciseRepository.getExercise(exerciseID)) } }

	private val currentSetNumber = MutableStateFlow("")
	private val currentWeight = MutableStateFlow("")
	private val currentReps = MutableStateFlow("")

	val setNumber = currentSetNumber.asLiveData()
	val weight = currentWeight.asLiveData()
	val reps = currentReps.asLiveData()


	init {
		viewModelScope.launch {
			currentExerciseValue.collectLatest {
				currentExerciseType.emit(it.exerciseTypeId)
				currentSetNumber.emit(it.setNumber.toString())
				currentWeight.emit(it.weight.toString())
				currentReps.emit(it.reps.toString())
			}
		}
	}

	/// sets the exercise type
	fun setExercise(exerciseTypeId: UUID) = viewModelScope.launch {
		currentExerciseType.emit(exerciseTypeId)
	}

	/// Sets the set number
	fun setSetNumber(set: String) = viewModelScope.launch {
		currentSetNumber.emit(set)
	}

	/// Sets the weight number
	fun setWeight(weight: String) = viewModelScope.launch {
		currentWeight.emit(weight)
	}

	/// sets the amount of reps
	fun setReps(reps: String) = viewModelScope.launch {
		currentReps.emit(reps)
	}

	fun finalise(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
		if (exerciseID != null) exerciseID.let {
			exerciseRepository.update(
				exerciseID = exerciseID,
				exerciseTypeID = currentExerciseType.value!!,
				setNumber = setNumber.value!!.toInt(),
				weight = weight.value!!.toDouble(),
				reps = reps.value!!.toInt()
			)
		} else {
			exerciseRepository.create(
				exercise = UUID.fromString(selectedExerciseType.value!!),
				setNumber = setNumber.value!!.toInt(),
				weight = weight.value!!.toDouble(),
				reps = reps.value!!.toInt()
			)
		}
		activity.finish()
	}
}