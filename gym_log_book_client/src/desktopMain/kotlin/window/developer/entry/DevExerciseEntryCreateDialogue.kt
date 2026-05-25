@file:OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)

package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.michael_bailey.gym_log_book.client.component.form.ExerciseTypeDropdownSelector
import net.michael_bailey.gym_log_book.client.util.scopedInject
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun DevExerciseEntryCreateDialogue(
	viewModel: DevExerciseEntryCreateDialogueViewModel = scopedInject(),
	onDismiss: () -> Unit = { }
) {

	val exerciseTypes by viewModel.exerciseTypes.collectAsState(initial = emptyList())
	var selectedType by viewModel.selectedExerciseType

	val selectedDateState = viewModel.selectedDateState
	val showDatePickerState = viewModel.showDatePickerState
	val formattedDate by viewModel.formatedDateText

	val weightFieldState = viewModel.weightFieldState
	val setFieldState = viewModel.setFieldState
	val repsFieldState = viewModel.repsFieldState

	DatePickerDialogue(
		showDatePickerState,
		selectedDateState
	)

	Dialog(
		onDismissRequest = onDismiss
	) {
		Card {
			Column(
				modifier = Modifier.padding(24.dp),
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				ExerciseTypeDropdownSelector(
					modifier = Modifier.fillMaxWidth(),
					exerciseTypes = exerciseTypes,
					selectedType = selectedType,
					onChange = { selectedType = it }
				)
				Box(
					modifier = Modifier.fillMaxWidth(),
				) {
					OutlinedTextField(
						value = formattedDate,
						onValueChange = {},
						readOnly = true,
						label = { Text("Date") },
						trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
						modifier = Modifier.fillMaxWidth()
					)
					Box(modifier = Modifier.matchParentSize().clickable { showDatePickerState.value = true })
				}
				OutlinedTextField(
					modifier = Modifier.fillMaxWidth(),
					state = weightFieldState,
					label = { Text("Weight") },
				)
				OutlinedTextField(
					modifier = Modifier.fillMaxWidth(),
					state = setFieldState,
					label = { Text("Set Number") },
				)
				OutlinedTextField(
					modifier = Modifier.fillMaxWidth(),
					state = repsFieldState,
					label = { Text("Repetitions") },
				)
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceEvenly
				) {
					Button(onClick = { viewModel.reset(); onDismiss() }) {
						Text("Cancel")
					}
					Button(onClick = viewModel::submit) {
						Text("Submit")
					}
				}
			}
		}
	}
}


@Composable
private fun DatePickerDialogue(
	showState: MutableState<Boolean>,
	dateState: DatePickerState,
) {

	var showDatePicker by showState

	if (showDatePicker) {
		DatePickerDialog(
			onDismissRequest = { showDatePicker = false },
			confirmButton = {
				TextButton(onClick = { showDatePicker = false }) { Text("OK") }
			},
			dismissButton = {
				TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
			}
		) {
			DatePicker(state = dateState)
		}
	}
}