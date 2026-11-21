@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_type

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import net.michael_bailey.gym_log_book.client.component.form.LocalExerciseTypeDropdownSelector
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun DevLocalExerciseTypeDeleteDialogue(
	removeTypes: List<Uuid>,
	exerciseTypes: List<ExerciseTypeEntity>,
	onDelete: (Uuid) -> Unit,
	onDismiss: () -> Unit,
) {

	var replacementTypeId by remember { mutableStateOf<Uuid?>(null) }

	Dialog(
		onDismissRequest = onDismiss,
		properties = DialogProperties(),
	) {
		Card {
			Column(
				modifier = Modifier.padding(24.dp),
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				Text(
					text = "Delete exercise type",
					style = MaterialTheme.typography.headlineSmall,
				)
				LocalExerciseTypeDropdownSelector(
					modifier = Modifier.fillMaxWidth(),
					exerciseTypes = exerciseTypes.filter { !removeTypes.contains(it.id) },
					selectedType = replacementTypeId,
					onChange = { replacementTypeId = it }
				)
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.End,
				) {
					TextButton(onClick = onDismiss) {
						Text("Cancel")
					}
					Button(
						onClick = {
							onDelete(replacementTypeId!!)
						},
						enabled = replacementTypeId != null,
					) {
						Text("Delete")
					}
				}
			}
		}
	}
}