@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_type

import androidx.compose.runtime.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DevLocalExerciseTypeSelectionState {
	var isSelectionModeShown by mutableStateOf(false)
	val selectedTypeIds = mutableStateListOf<Uuid>()
	val selectedCount by derivedStateOf { selectedTypeIds.size }

	val deleteEnabled by derivedStateOf { selectedCount > 0 }

	fun enableSelection() {
		isSelectionModeShown = true
	}

	fun disableSelection() {
		selectedTypeIds.clear()
		isSelectionModeShown = false
	}
}