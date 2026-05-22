@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.michael_bailey.gym_log_book.client.exercise.state.ExerciseEntryCreateFormState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun ExerciseEntryCreateFormDialogue(
	formState: ExerciseEntryCreateFormState,
	onDismiss: () -> Unit = { },
	onSubmit: () -> Unit
) {
	Dialog(
		onDismissRequest = onDismiss,
	) {
		Card {
			Column(
				modifier = Modifier.padding(24.dp),
			) {
				ExerciseEntryCreateForm(
					state = formState,
					onCancel = onDismiss,
					onSubmit = onSubmit
				)
			}
		}
	}
}

@Preview
@Composable
fun ExerciseEntryCreateFormDialogue_Preview() {

	val exerciseTypes = mutableStateOf(
		buildMap {
			(0 until 5)
				.forEach { this[Uuid.random()] = "Type $it" }
		}
	)

	val locale = rememberDatePickerState().locale

	val formState = ExerciseEntryCreateFormState(
		exerciseTypes = exerciseTypes,
		initialTime = 19 to 30,
		locale = locale
	)
	ExerciseEntryCreateFormDialogue(
		formState = formState,
	) {}
}