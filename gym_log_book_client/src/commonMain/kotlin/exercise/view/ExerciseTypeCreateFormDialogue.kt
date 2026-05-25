package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.michael_bailey.gym_log_book.client.exercise.state.ExerciseTypeCreateFormState

@Composable
fun ExerciseTypeCreateFormDialogue(
	modifier: Modifier = Modifier,
	formState: ExerciseTypeCreateFormState = ExerciseTypeCreateFormState(),
	onDismiss: () -> Unit = { },
	onSubmit: () -> Unit
) {
	Dialog(
		onDismissRequest = onDismiss,
	) {
		Card {
			Column {
				ExerciseTypeCreateForm(
					modifier = modifier.padding(24.dp),
					formState = formState,
					onDismiss = onDismiss,
					onSubmit = onSubmit,
				)
			}
		}
	}
}

@Preview
@Composable
fun ExerciseTypeCreateFormDialogue_Preview() {
	ExerciseTypeCreateFormDialogue { }
}