@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DevExerciseEntryTabPageViewModel(
	private val exerciseEntryService: ExerciseEntryService,
) : ViewModel() {

	val exerciseEntriesList = exerciseEntryService.allEntries.map { it.toList() }

	val isSelectionModeShown = mutableStateOf(false)
	val selectedEntryIds = mutableStateListOf<Uuid>()

	fun showSelectionMode() {
		isSelectionModeShown.value = true
	}

	fun hideSelectionMode() {
		isSelectionModeShown.value = false
		selectedEntryIds.clear()
	}

	fun toggleExerciseTypeSelection(id: Uuid) {
		if (id in selectedEntryIds) {
			selectedEntryIds.remove(id)
			return
		}
		selectedEntryIds.add(id)
	}

	fun deleteExerciseEntry(id: Uuid) {

	}
}