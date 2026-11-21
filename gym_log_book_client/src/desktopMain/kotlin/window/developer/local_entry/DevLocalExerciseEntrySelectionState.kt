@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_entry

import androidx.compose.runtime.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DevLocalExerciseEntrySelectionState {
	var isSelectionModeShown by mutableStateOf(false)
	val selectedEntryIds = mutableStateListOf<Uuid>()
	val selectedCount by derivedStateOf { selectedEntryIds.size }

	val deleteEnabled by derivedStateOf { selectedCount > 0 }

	fun enableSelection() {
		isSelectionModeShown = true
	}

	fun disableSelection() {
		selectedEntryIds.clear()
		isSelectionModeShown = false
	}
}