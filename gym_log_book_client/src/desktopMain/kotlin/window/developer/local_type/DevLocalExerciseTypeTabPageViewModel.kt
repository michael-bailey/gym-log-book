@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_type

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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

	val isNewTypeDialogueShown = mutableStateOf(false)
	val isSelectionModeShown = mutableStateOf(false)
	val selectedTypeIds = mutableStateListOf<Uuid>()

	fun showNewTypeDialogue() {
		isNewTypeDialogueShown.value = true
	}

	fun hideNewTypeDialogue() {
		isNewTypeDialogueShown.value = false
	}

	fun showSelectionMode() {
		isSelectionModeShown.value = true
	}

	fun hideSelectionMode() {
		isSelectionModeShown.value = false
		selectedTypeIds.clear()
	}

	fun toggleExerciseTypeSelection(id: Uuid) {
		if (id in selectedTypeIds) {
			selectedTypeIds.remove(id)
			return
		}
		selectedTypeIds.add(id)
	}

	fun addExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	) = viewModelScope.launch {
		exerciseTypeService.createNewLocalType(
			name = name.trim(),
			equipmentClass = equipmentClass,
		)
		hideNewTypeDialogue()
	}

	fun deleteSelectedExerciseTypes() = viewModelScope.launch {
		val idsToDelete = selectedTypeIds.toList()
		if (idsToDelete.isEmpty()) {
			return@launch
		}

		exerciseTypeService.deleteTypes(idsToDelete)
		hideSelectionMode()
	}

}
