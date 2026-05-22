@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.state

import androidx.compose.runtime.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseTypeSelectionState(
	val selectableTypesMap: State<Map<Uuid, String>>,
) {

	var isDropdownShown: MutableState<Boolean> = mutableStateOf(false)
	var selectedTypeState: MutableState<Uuid?> = mutableStateOf(value = null)

	val selectedTypeString = derivedStateOf {
		val selectedType = selectedTypeState.value ?: return@derivedStateOf "No Selection"
		val selectedTypeString = selectableTypesMap.value[selectedType] ?: "No Selection"
		selectedTypeString
	}

	fun onSelected(id: Uuid) {
		isDropdownShown.value = false
		selectedTypeState.value = id
	}

	companion object {
		@Composable
		fun remembered(
			selectableTypesMap: MutableState<Map<Uuid, String>>,
		) = remember {
			ExerciseTypeSelectionState(
				selectableTypesMap = selectableTypesMap,
			)
		}
	}
}