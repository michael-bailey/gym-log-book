@file:OptIn(ExperimentalUuidApi::class, InternalAPI::class)

package net.michael_bailey.gym_log_book.client.component.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.utils.io.*
import net.michael_bailey.gym_log_book.client.state.ExerciseTypeSelectionState
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseTypeSelector(
	modifier: Modifier = Modifier,
	selectionState: ExerciseTypeSelectionState,
) {

	var isDropdownShown by selectionState.isDropdownShown
	val selectedTypeString by selectionState.selectedTypeString
	val typeMap by selectionState.selectableTypesMap

	Column(modifier = modifier) {
		ExposedDropdownMenuBox(
			expanded = isDropdownShown,
			onExpandedChange = { isDropdownShown = it }  // let M3 manage the toggle
		) {
			OutlinedTextField(
				modifier = Modifier
					.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
					.fillMaxWidth(),
				value = selectedTypeString,
				onValueChange = {},
				readOnly = true,  // non-editable dropdown
				label = { Text("Exercise...") },
				trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownShown) },
				colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
			)

			ExposedDropdownMenu(
				expanded = isDropdownShown,
				onDismissRequest = { isDropdownShown = false }
			) {
				for ((id, name) in typeMap) {
					DropdownMenuItem(
						text = { Text(name) },
						onClick = { selectionState.onSelected(id) }
					)
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseTypeDropdownSelector(
	modifier: Modifier = Modifier,
	exerciseTypes: List<ExerciseType>,
	selectedType: Uuid?,
	onChange: (Uuid) -> Unit
) {
	val typeNameMap = remember(exerciseTypes) {
		exerciseTypes.associate { it.id to it.name }
	}

	var dropdownExpanded by remember { mutableStateOf(false) }

	val textFieldState = rememberTextFieldState(
		initialText = typeNameMap[selectedType] ?: ""
	)

	// Keep the displayed text in sync if selectedType is changed externally
	LaunchedEffect(selectedType, typeNameMap) {
		textFieldState.edit {
			replace(0, length, typeNameMap[selectedType] ?: "")
		}
	}

	Column(modifier = modifier) {
		ExposedDropdownMenuBox(
			expanded = dropdownExpanded,
			onExpandedChange = { dropdownExpanded = it }  // let M3 manage the toggle
		) {
			OutlinedTextField(
				modifier = Modifier
					.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
					.fillMaxWidth(),
				state = textFieldState,
				readOnly = true,  // non-editable dropdown
				label = { Text("Exercise...") },
				trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
				colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
			)

			ExposedDropdownMenu(
				expanded = dropdownExpanded,
				onDismissRequest = { dropdownExpanded = false }
			) {
				for ((id, name) in typeNameMap) {
					DropdownMenuItem(
						text = { Text(name) },
						onClick = {
							onChange(id)
							dropdownExpanded = false
						}
					)
				}
			}
		}
	}
}

@Preview
@Composable
fun ExerciseTypeSelector_Preview() {

	val state = ExerciseTypeSelectionState(
		selectableTypesMap = mutableStateOf(
			mapOf(
				Uuid.random() to "Type 1",
				Uuid.random() to "Type 2",
				Uuid.random() to "Type 3",
				Uuid.random() to "Type 4",
				Uuid.random() to "Type 5",
			)
		)
	)

	ExerciseTypeSelector(
		selectionState = state
	)
}