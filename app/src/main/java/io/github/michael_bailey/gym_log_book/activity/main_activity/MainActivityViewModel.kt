package io.github.michael_bailey.gym_log_book.activity.main_activity

import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity_v2.AmendExerciseActivityV2IntentUtils
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseEntry
import io.github.michael_bailey.gym_log_book.lib.PeriodGroup
import io.github.michael_bailey.gym_log_book.lib.gatekeeper.Gatekeeper
import io.github.michael_bailey.gym_log_book.repository.ExerciseEntryRepository
import io.github.michael_bailey.gym_log_book.repository.ExerciseTypeRepository
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

	val gatekeeper: Gatekeeper
) : ViewModel() {

	var removedID = mutableStateOf<UUID?>(null)
	var selectedReplacementType = mutableStateOf<UUID?>(null)

	val isExercisesEmpty = exerciseEntryRepository.isEmpty.asLiveData()

	val timeExerciseGroupedList =
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

	private val exerciseTypeListFlow = exerciseTypeRepository.exerciseTypes
	val exerciseTypeList = exerciseTypeListFlow.asLiveData()
	val weightEntryList = weightEntryRepository.weightEntryList.asLiveData()
	val isExerciseTypeListEmpty = exerciseTypeRepository.isEmpty.asLiveData()

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

	fun deleteExerciseEntry(uuid: UUID) = viewModelScope.launch(Dispatchers.IO) {
		exerciseEntryRepository.delete(
			uuid
		)
	}

	fun deleteWeightEntry(uuid: UUID) = viewModelScope.launch(Dispatchers.IO) {
		weightEntryRepository.delete(uuid)
	}
}