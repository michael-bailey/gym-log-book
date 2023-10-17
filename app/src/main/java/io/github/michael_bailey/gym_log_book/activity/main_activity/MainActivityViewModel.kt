package io.github.michael_bailey.gym_log_book.activity.main_activity

import android.app.Activity
import android.app.Application
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.gym_log_book.activity.add_exercise_type_activity.AddExerciseTypeActivityIntentUtils
import io.github.michael_bailey.gym_log_book.activity.add_weight_activity.AddWeightActivityIntentUtils
import io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity_v2.AmendExerciseActivityV2IntentUtils
import io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivityIntentUtils
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseEntry
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseType
import io.github.michael_bailey.gym_log_book.lib.PeriodGroup
import io.github.michael_bailey.gym_log_book.lib.gatekeeper.Gatekeeper
import io.github.michael_bailey.gym_log_book.lib.interfaces.view_model.IExerciseTypeViewModel
import io.github.michael_bailey.gym_log_book.lib.interfaces.view_model.IExerciseViewModel
import io.github.michael_bailey.gym_log_book.lib.one_shot.OneShotPreference
import io.github.michael_bailey.gym_log_book.repository.ExerciseEntryRepository
import io.github.michael_bailey.gym_log_book.repository.ExerciseTypeRepository
import io.github.michael_bailey.gym_log_book.repository.ReminderRepository
import io.github.michael_bailey.gym_log_book.repository.WeightEntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
	private val exerciseTypeRepository: ExerciseTypeRepository,
	private val exerciseEntryRepository: ExerciseEntryRepository,
	private val weightEntryRepository: WeightEntryRepository,
	private val reminderRepository: ReminderRepository,
	val gatekeeper: Gatekeeper,
	application: Application,
) : AndroidViewModel(application), IExerciseTypeViewModel, IExerciseViewModel {

	init {
		reminderRepository.queryCalendars()
	}

	// mark: - exercise page state
	override val exerciseListState = LazyListState(
		0,
		0
	)

	override val isExercisesEmpty = exerciseEntryRepository.isEmpty.asLiveData()
	override val timeExerciseGroupedList: LiveData<Map<String, List<EntExerciseEntry>>> =
		exerciseEntryRepository.timeExerciseGroupedList.map {
			val output = mutableMapOf<String, List<EntExerciseEntry>>()
			val sortedList = it.toList().sortedByDescending { it.first }.toMap()
			for (date in sortedList.keys) {
				val group =
					PeriodGroup.getPeriodGroup(period = date.until(LocalDate.now()))
						.toString()
				val list = output[group]
				if (list == null) {
					output[group] =
						sortedList[date]!!.toList().sortedByDescending { it.createdTime }
				} else {
					output[group] =
						(list + sortedList[date]!!.toList()).sortedByDescending { it.createdTime }
				}
			}
			output
		}.asLiveData()

	// mark: - exercise type page state
	override val exerciseTypeList: LiveData<List<EntExerciseType>>
		get() = exerciseTypeRepository.exerciseTypes.asLiveData()
	val exerciseTypeListState = LazyListState(
		0,
		0
	)

	val weightListState = LazyListState(
		0,
		0
	)

	private val _onbaordingOneShot = OneShotPreference("onboarding_complete")

	val removedID = mutableStateOf<UUID?>(null)
	val selectedReplacementType = mutableStateOf<UUID?>(null)


	val weightEntryList = weightEntryRepository.weightEntryList.asLiveData()
	val isExerciseTypeListEmpty = exerciseTypeRepository.isEmpty.asLiveData()
	val onboardingComplete = _onbaordingOneShot.isConsumedFlow().asLiveData()

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

	override fun amendExerciseEntry(id: UUID) {
		AmendExerciseActivityV2IntentUtils.startAmendActivity(
			getApplication<Application>().applicationContext,
			id
		)
	}

	override fun deleteExerciseEntry(uuid: UUID) {
		viewModelScope.launch(Dispatchers.IO) {
			exerciseEntryRepository.delete(
				uuid
			)
		}
	}


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