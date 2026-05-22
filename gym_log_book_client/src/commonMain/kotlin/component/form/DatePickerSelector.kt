package net.michael_bailey.gym_log_book.client.component.form

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

@Composable
fun DatePickerSelector(
	dateState: DatePickerState,
) {

	val showDialogue = mutableStateOf(false)

	val formattedState = derivedStateOf {
		dateState.selectedDateMillis
			?.let { millis ->
				val date = Instant.fromEpochMilliseconds(millis)
					.toLocalDateTime(TimeZone.currentSystemDefault()).date
				"${date.day}/${date.month.number}/${date.year}"
			} ?: "Select date"
	}

	OutlinedButton(onClick = { showDialogue.value = true }) {
		Text(text = formattedState.value)
	}

	DatePickerDialogue(
		showState = showDialogue,
		dateState = dateState
	)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
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

@Preview
@Composable
fun DatePickerSelector_Preview() {

	val state = rememberDatePickerState()

	DatePickerSelector(
		dateState = state,
	)
}