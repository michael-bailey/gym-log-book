package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.dialogue

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity_v2.components.ExerciseTypeDropdownSelector
import java.util.UUID

@Composable
fun DeleteExerciseTypeDialogue(
	removedID: UUID,
	selectedType: UUID?,
	typeList: Map<UUID, String>,
	onSelect: (UUID) -> Unit,
	onDisable: () -> Unit,
	onSubmit: () -> Unit,
) {
	AlertDialog(
		onDismissRequest = onDisable,
		title = { Text("Replace ${typeList[removedID]} with...") },
		text = {
			ExerciseTypeDropdownSelector(
				exercises = typeList.filterKeys { it != removedID },
				selectedType = typeList[selectedType] ?: "",
				setExercise = onSelect
			)
		},

		confirmButton = {
			Button(
				enabled = (selectedType != null),
				onClick = onSubmit
			) {
				Text(text = "Submit")
			}
		},

		dismissButton = {
			Button(onClick = onDisable) {
				Text(text = "Cancel")
			}
		},
	)
}