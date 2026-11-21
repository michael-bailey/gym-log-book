@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.exercise.service.ILocalExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DevLocalExerciseTypeTabPageViewModel(
	private val exerciseTypeService: ILocalExerciseTypeService,
) : ViewModel() {

	val exerciseTypeList = exerciseTypeService.localExerciseTypes
		.map { it.toList() }

	fun addExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	) = viewModelScope.launch {
		exerciseTypeService.createNewLocalType(
			name = name.trim(),
			equipmentClass = equipmentClass,
		)
	}

	fun deleteSelectedExerciseTypes(selectedTypeIds: List<Uuid>, replacementId: Uuid) = viewModelScope.launch {
		val idsToDelete = selectedTypeIds.toList()
		if (idsToDelete.isEmpty()) {
			return@launch
		}

		exerciseTypeService.deleteLocalTypes(ids = idsToDelete, replacementId = replacementId)
	}

}
