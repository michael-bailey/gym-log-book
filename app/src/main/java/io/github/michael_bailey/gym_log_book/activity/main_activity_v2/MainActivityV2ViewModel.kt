package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity_v2.AmendExerciseActivityV2IntentUtils
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseDataManager
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseTypeDataManager
import io.github.michael_bailey.gym_log_book.data_manager.WeightDataManager
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseEntry
import io.github.michael_bailey.gym_log_book.lib.PeriodGroup
import io.github.michael_bailey.gym_log_book.lib.gatekeeper.Gatekeeper
import io.github.michael_bailey.gym_log_book.repository.ExerciseEntryRepository
import io.github.michael_bailey.gym_log_book.repository.ExerciseTypeRepository
import io.github.michael_bailey.gym_log_book.repository.WeightEntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainActivityV2ViewModel @Inject constructor(
	private val exerciseTypeDataManager: ExerciseTypeDataManager,
	private val exerciseDataManager: ExerciseDataManager,
	private val weightDataManager: WeightDataManager,

	private val exerciseTypeRepository: ExerciseTypeRepository,
	private val exerciseEntryRepository: ExerciseEntryRepository,
	private val weightEntryRepository: WeightEntryRepository,

	val gatekeeper: Gatekeeper

) : ViewModel() {

	var removedID = mutableStateOf<UUID?>(null)
	var selectedReplacementType = mutableStateOf<UUID?>(null)

	val isExercisesEmpty = exerciseEntryRepository.isEmpty.asLiveData()
	val isExerciseTypeDatabaseViewEnabled = gatekeeper
		.evalState("database_exercise_type_view")
		.onEach { Log.d("Michael", "got value $it") }
		.asLiveData()

	val timeExerciseGroupedList =
		exerciseEntryRepository.timeExerciseGroupedList.map {
			var output = mutableMapOf<String, List<EntExerciseEntry>>()

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

	fun deleteExercise(id: Int) {
		exerciseDataManager.delete(id)
	}

	@Deprecated("This uses data manager remove this where necessary")
	fun initiateExerciseTypeDeletion(id: Int) {
		exerciseTypeDataManager.delete(id)
	}

	fun amendExerciseType(id: UUID) = viewModelScope.launch {
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

		exerciseEntryRepository.removeAndReplaceType(removedID, selectedType)

		this@MainActivityV2ViewModel.selectedReplacementType.value = null
		this@MainActivityV2ViewModel.removedID.value = null

	}

	fun amendExerciseEntry(activity: Activity, uuid: UUID) =
		AmendExerciseActivityV2IntentUtils.startAmendActivity(activity, uuid)

	fun deleteExerciseEntry(uuid: UUID) = viewModelScope.launch(Dispatchers.IO) {
		exerciseEntryRepository.delete(
			uuid
		)
	}
}