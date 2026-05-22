@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.michael_bailey.gym_log_book.client.component.form.DatePickerSelector
import net.michael_bailey.gym_log_book.client.component.form.ExerciseTypeSelector
import net.michael_bailey.gym_log_book.client.component.form.TimePickerSelector
import net.michael_bailey.gym_log_book.client.component.transform.RepsTransformer
import net.michael_bailey.gym_log_book.client.component.transform.SetNumberTransformer
import net.michael_bailey.gym_log_book.client.component.transform.WeightTransformer
import net.michael_bailey.gym_log_book.client.exercise.state.ExerciseEntryCreateFormState
import net.michael_bailey.gym_log_book.client.state.ExerciseTypeSelectionState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun ExerciseEntryCreateForm(
	modifier: Modifier = Modifier,
	state: ExerciseEntryCreateFormState,
	onCancel: () -> Unit,
	onSubmit: () -> Unit
) {
	ExerciseEntryCreateForm(
		modifier = modifier,
		dateState = state.dateState,
		timeState = state.timeState,
		exerciseTypeSelectorState = state.exerciseTypeSelectionState,
		weightTextFieldState = state.weightTextFieldState,
		setNumberTextFieldState = state.setNumberTextFieldState,
		repsTextFieldState = state.repsTextFieldState,
		canSubmit = state.canSubmit,
		onCancel = onCancel,
		onSubmit = onSubmit,
	)
}

@Composable
fun ExerciseEntryCreateForm(
	modifier: Modifier = Modifier,
	dateState: DatePickerState,
	timeState: TimePickerState,
	exerciseTypeSelectorState: ExerciseTypeSelectionState,
	weightTextFieldState: TextFieldState,
	setNumberTextFieldState: TextFieldState,
	repsTextFieldState: TextFieldState,
	canSubmit: State<Boolean>,
	onCancel: () -> Unit,
	onSubmit: () -> Unit
) {
	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		ExerciseTypeSelector(
			selectionState = exerciseTypeSelectorState
		)
		Row(
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			DatePickerSelector(
				dateState = dateState
			)
			TimePickerSelector(
				timeState = timeState
			)
		}
		OutlinedTextField(
			modifier = Modifier.fillMaxWidth(),
			state = weightTextFieldState,
			label = { Text("Weight") },
			inputTransformation = WeightTransformer(),
			outputTransformation = WeightTransformer(),
		)
		OutlinedTextField(
			modifier = Modifier.fillMaxWidth(),
			state = setNumberTextFieldState,
			label = { Text("Set Number") },
			inputTransformation = SetNumberTransformer(),
			outputTransformation = SetNumberTransformer(),
		)
		OutlinedTextField(
			modifier = Modifier.fillMaxWidth(),
			state = repsTextFieldState,
			label = { Text("Reps") },
			inputTransformation = RepsTransformer(),
			outputTransformation = RepsTransformer(),
		)
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {

			val canSubmit by canSubmit

			OutlinedButton(onClick = onCancel) {
				Text("Cancel")
			}
			Button(
				onClick = onSubmit,
				enabled = canSubmit,
			) {
				Text("Submit")
			}
		}
	}
}

@Preview
@Composable
fun ExerciseEntryCreateForm_Preview() {

	val selectableTypesMap = mutableStateOf(
		mapOf(
			Uuid.random() to "Type 1",
			Uuid.random() to "Type 2",
			Uuid.random() to "Type 3",
			Uuid.random() to "Type 4",
			Uuid.random() to "Type 5",
		)
	)

	val exerciseCreateFormState = ExerciseEntryCreateFormState.remembered(
		exerciseTypeState = selectableTypesMap
	)

	ExerciseEntryCreateForm(
		modifier = Modifier,
		state = exerciseCreateFormState,
		onSubmit = {},
		onCancel = {}
	)
}