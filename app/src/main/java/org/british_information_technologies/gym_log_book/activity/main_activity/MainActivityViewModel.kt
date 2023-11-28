package org.british_information_technologies.gym_log_book.activity.main_activity

import android.app.Activity
import android.app.Application
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.activity.add_exercise_type_activity.AddExerciseTypeActivityIntentUtils
import org.british_information_technologies.gym_log_book.activity.add_weight_activity.AddWeightActivityIntentUtils
import org.british_information_technologies.gym_log_book.activity.amend_exercise_activity_v2.AmendExerciseActivityV2IntentUtils
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivityIntentUtils
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseEntry
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import org.british_information_technologies.gym_log_book.lib.interfaces.view_model.IExerciseEntryListViewModel
import org.british_information_technologies.gym_log_book.lib.interfaces.view_model.IExerciseTypeViewModel
import org.british_information_technologies.gym_log_book.lib.one_shot.OneShotPreference
import org.british_information_technologies.gym_log_book.repository.ExerciseEntryMappingsRepository
import org.british_information_technologies.gym_log_book.repository.ExerciseEntryRepository
import org.british_information_technologies.gym_log_book.repository.ExerciseTypeMappingsRepository
import org.british_information_technologies.gym_log_book.repository.ExerciseTypeRepository
import org.british_information_technologies.gym_log_book.repository.ReminderRepository
import org.british_information_technologies.gym_log_book.repository.WeightEntryRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
	private val exerciseTypeRepository: ExerciseTypeRepository,
	private val exerciseTypeMappingsRepository: ExerciseTypeMappingsRepository,
	private val exerciseEntryRepository: ExerciseEntryRepository,
	private val exerciseEntryMappingsRepository: ExerciseEntryMappingsRepository,
	private val weightEntryRepository: WeightEntryRepository,
	private val reminderRepository: ReminderRepository,
	application: Application,
) : AndroidViewModel(application), IExerciseEntryListViewModel,
	IExerciseTypeViewModel {

	// preference state
	private val _onbaordingOneShot = OneShotPreference("onboarding_complete")

	// live data
	override val timeExerciseGroupedList: LiveData<Map<String, List<EntExerciseEntry>>>
		get() = exerciseEntryMappingsRepository.exercisesGroupedByTime.asLiveData()
	override val exerciseNameMap: LiveData<Map<UUID, String>>
		get() = exerciseTypeMappingsRepository.exerciseIdToName.asLiveData()
	override val exerciseEntryList: LiveData<List<EntExerciseEntry>>
		get() = exerciseEntryRepository.exercises.asLiveData()

	override val isExercisesEmpty = exerciseEntryRepository.isEmpty.asLiveData()

	val weightEntryList = weightEntryRepository.weightEntryList.asLiveData()
	val isExerciseTypeListEmpty = exerciseTypeRepository.isEmpty.asLiveData()
	val onboardingComplete = _onbaordingOneShot.isConsumedFlow().asLiveData()

	override val exerciseTypeList: LiveData<List<EntExerciseType>>
		get() = exerciseTypeRepository.exerciseTypes.asLiveData()

	// ui state
	override val exerciseListState = LazyListState(
		0,
		0
	)

	val exerciseTypeListState = LazyListState(
		0,
		0
	)

	val weightListState = LazyListState(
		0,
		0
	)

	val removedID = mutableStateOf<UUID?>(null)
	val selectedReplacementType = mutableStateOf<UUID?>(null)

	init {
		reminderRepository.queryCalendars()
	}

	override fun modifyExerciseEntry(
		newEntity: EntExerciseEntry
	) = viewModelScope.launch(Dispatchers.IO) {
		exerciseEntryRepository.update(
			newEntity
		)
	}

	override fun deleteExerciseEntry(
		exerciseID: UUID
	) = viewModelScope.launch(Dispatchers.IO) {
		exerciseEntryRepository.delete(exerciseID)
	}

	fun setSelectedReplacementType(id: UUID) {
		selectedReplacementType.value = id
	}

	fun initiateExerciseTypeDeletion(id: UUID) =
		viewModelScope.launch(Dispatchers.IO) {
			removedID.value = id
		}

	fun clearExerciseTypeDeletion() {
		removedID.value = null
		selectedReplacementType.value = null
	}

	fun finaliseExerciseRemoval() = viewModelScope.launch(Dispatchers.IO) {
		val removedID = removedID.value
		val selectedType = selectedReplacementType.value

		if (removedID == null || selectedType == null) {
			return@launch
		}

		exerciseTypeRepository.removeAndReplaceType(removedID, selectedType)

		this@MainActivityViewModel.selectedReplacementType.value = null
		this@MainActivityViewModel.removedID.value = null

	}

	fun amendExerciseEntry(activity: Activity, uuid: UUID) =
		AmendExerciseActivityV2IntentUtils.startAmendActivity(activity, uuid)

	fun deleteWeightEntry(uuid: UUID) = viewModelScope.launch(Dispatchers.IO) {
		weightEntryRepository.delete(uuid)
	}

	fun startExerciseSetGuideActivity() {
		ExerciseSetGuideActivityIntentUtils.startExerciseSetGuideActivity(
			getApplication()
		)
	}

	fun startAddWeightActivity() {
		AddWeightActivityIntentUtils.startAddWeightActivity(getApplication())
	}

	fun startAddExerciseTypeActivity() {
		AddExerciseTypeActivityIntentUtils.startAddExerciseTypeActivity(
			getApplication()
		)
	}
}