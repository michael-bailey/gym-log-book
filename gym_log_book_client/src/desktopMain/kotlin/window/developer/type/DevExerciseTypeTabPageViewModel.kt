@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.type

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DevExerciseTypeTabPageViewModel(
	private val exerciseTypeService: ExerciseTypeService
) : ViewModel() {

	val exerciseTypeList = exerciseTypeService.exerciseTypes.map { it.toList() }

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
			if (selectedTypeIds.isEmpty()) {
				isSelectionModeShown.value = false
			}
			return
		}

		if (!isSelectionModeShown.value) {
			isSelectionModeShown.value = true
		}
		selectedTypeIds.add(id)
	}

	fun addExerciseType(
		name: String,
		equipmentClass: EquipmentClass,
	) = viewModelScope.launch {
		exerciseTypeService.createNewType(
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
