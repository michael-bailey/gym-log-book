@file:OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)

package net.michael_bailey.gym_log_book.client.exercise.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import net.michael_bailey.gym_log_book.client.state.ExerciseTypeSelectionState
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryCreateFormState(
	private val exerciseTypes: State<Map<Uuid, String>>,
	private val initialTime: Pair<Int, Int>,
	private val locale: CalendarLocale
) {

	val exerciseTypeSelectionState = ExerciseTypeSelectionState(
		selectableTypesMap = exerciseTypes
	)

	val dateState = DatePickerState(locale = locale)
	val timeState = TimePickerState(
		initialHour = initialTime.first,
		initialMinute = initialTime.second,
		is24Hour = true
	)

	val weightTextFieldState = TextFieldState("0.0")
	val setNumberTextFieldState = TextFieldState("0")
	val repsTextFieldState = TextFieldState("0")

	val selectedTimeInstant: State<Instant?> = derivedStateOf {
		val selectedDate = dateState.selectedDateMillis ?: return@derivedStateOf null
		val timeMillis = timeState.hour.milliseconds + timeState.minute.milliseconds
		selectedDate + timeMillis.inWholeMilliseconds
		Instant.fromEpochMilliseconds(selectedDate + timeMillis.inWholeMilliseconds)
	}

	val canSubmit = derivedStateOf {
		if (exerciseTypeSelectionState.selectedTypeState.value == null) return@derivedStateOf false
		if (selectedTimeInstant.value == null) return@derivedStateOf false

		if (getWeight() == null) return@derivedStateOf false
		if (getSetNumber() == null) return@derivedStateOf false
		if (getReps() == null) return@derivedStateOf false

		true
	}

	fun getWeight(): Double? = weightTextFieldState.text.toString().toDoubleOrNull()
	fun getSetNumber(): Int? = setNumberTextFieldState.text.toString().toIntOrNull()
	fun getReps(): Int? = repsTextFieldState.text.toString().toIntOrNull()

	companion object {
		@Composable
		fun remembered(
			exerciseTypeState: State<Map<Uuid, String>>,
			initialTime: Pair<Int, Int> = 0 to 0,
			// this is dumb
			locale: CalendarLocale = rememberDatePickerState().locale,
		) = remember {
			ExerciseEntryCreateFormState(
				exerciseTypes = exerciseTypeState,
				locale = locale,
				initialTime = initialTime,
			)
		}
	}
}