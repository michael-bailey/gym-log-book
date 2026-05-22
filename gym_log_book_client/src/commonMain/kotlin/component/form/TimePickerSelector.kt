@file:OptIn(ExperimentalMaterial3Api::class)

package net.michael_bailey.gym_log_book.client.component.form

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TimePickerSelector(
	timeState: TimePickerState,
) {

	val showDialogue = mutableStateOf(false)

	val formattedState = derivedStateOf {
		"${timeState.hour.toString().padStart(2, '0')}:${timeState.minute.toString().padStart(2, '0')}"
	}

	OutlinedButton(onClick = { showDialogue.value = true }) {
		Text(text = formattedState.value)
	}

	TimePickerDialogue(
		showState = showDialogue,
		timeState = timeState
	)
}

@Composable
private fun TimePickerDialogue(
	showState: MutableState<Boolean>,
	timeState: TimePickerState,
) {

	var showDatePicker by showState

	if (showDatePicker) {
		TimePickerDialog(
			onDismissRequest = { showDatePicker = false },
			confirmButton = {
				TextButton(onClick = { showDatePicker = false }) { Text("OK") }
			},
			title = { Text("Select Time") }
		) {
			TimePicker(timeState)
		}
	}
}

@Preview
@Composable
fun TimePickerSelector_Preview() {

	val state = rememberTimePickerState(
		is24Hour = true
	)

	TimePickerSelector(
		timeState = state,
	)
}