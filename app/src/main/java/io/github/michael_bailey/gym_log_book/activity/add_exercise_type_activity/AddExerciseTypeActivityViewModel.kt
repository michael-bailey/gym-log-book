package io.github.michael_bailey.gym_log_book.activity.add_exercise_type_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.gym_log_book.lib.validation.ExerciseVerficationUtils
import io.github.michael_bailey.gym_log_book.repository.ExerciseTypeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExerciseTypeActivityViewModel @Inject constructor(
	private val exerciseTypeRepository: ExerciseTypeRepository
) : ViewModel() {

	private val currentExerciseName = MutableStateFlow("")
	private val currentUsesUserWeight = MutableStateFlow(false)

	val exerciseName = currentExerciseName.asLiveData()
	val isUsingUserWeight = currentUsesUserWeight.asLiveData()

	val canSubmit = currentExerciseName.map { name ->
		ExerciseVerficationUtils.verifyString(name).isSuccess
	}.asLiveData()

	fun setExerciseName(value: String) = viewModelScope.launch {
		currentExerciseName.emit(value)
	}

	fun setIsUsingUserWeight(value: Boolean) = viewModelScope.launch {
		currentUsesUserWeight.emit(value)
	}

	fun finalise() = viewModelScope.launch {
		exerciseTypeRepository.create(
			currentExerciseName.value,
			currentUsesUserWeight.value
		)
	}
}