package org.british_information_technologies.gym_log_book.activity.amend_exercise_type_activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.activity.amend_exercise_activity_v2.AmendExerciseActivityV2IntentUtils.INTENT_ID
import org.british_information_technologies.gym_log_book.repository.ExerciseTypeRepository
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class AmendExerciseTypeViewModel @Inject constructor(
	private val exerciseTypeRepository: ExerciseTypeRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	private val exerciseTypeID = savedStateHandle.get<UUID>(INTENT_ID)

	private val currentName = MutableStateFlow("")
	private val currentUsesUserWeight = MutableStateFlow(false)

	init {
		viewModelScope.launch {
			exerciseTypeRepository.getExerciseType(exerciseTypeID).apply {
				this?.let {
					currentName.emit(this.name)
					currentUsesUserWeight.emit(this.usesUserWeight)
				}
			}
		}
	}
}