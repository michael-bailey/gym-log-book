@file:OptIn(ExperimentalUuidApi::class)

package home.tabs.type

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.client.home.tabs.type.IExerciseTypeTabViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.type.IExerciseTypeTabViewModel.ExerciseTypeViewData
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseTypeTabViewModel(
	private val exerciseTypeService: ExerciseTypeService,
) : ViewModel(), IExerciseTypeTabViewModel {

	private val _typeMap = mutableStateOf<Map<Uuid, ExerciseTypeViewData>>(emptyMap())
	private val _typeList = mutableStateOf<List<ExerciseTypeViewData>>(emptyList())

	override val typeMap: State<Map<Uuid, ExerciseTypeViewData>> = _typeMap
	override val typeList: State<List<ExerciseTypeViewData>> = _typeList

	init {
		viewModelScope.launch { collectTypes() }
	}

	private suspend fun collectTypes() {
		exerciseTypeService.exerciseTypes
			.map(::mapToViewData)
			.map { it.toList() }
			.collect { _typeList.value = it }
	}

	private fun mapToViewData(coll: Collection<ExerciseType>): List<ExerciseTypeViewData> = coll.map {
		ExerciseTypeViewData(
			name = it.name,
			equipmentClass = it.equipmentClass.toString(),
			isUsingUserWeight = it.isUsingUserWeight
		)
	}
}