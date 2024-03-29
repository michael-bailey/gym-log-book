package org.british_information_technologies.gym_log_book.activity.main_activity.dialogue

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.british_information_technologies.gym_log_book.lib.componenets.ExerciseTypeDropdownSelector
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