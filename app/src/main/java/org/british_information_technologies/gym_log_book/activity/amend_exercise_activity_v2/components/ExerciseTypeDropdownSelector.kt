package org.british_information_technologies.gym_log_book.activity.amend_exercise_activity_v2.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseTypeDropdownSelector(
	exercises: Map<UUID, String>,
	selectedType: String?,
	setExercise: (UUID) -> Unit
) {
	var dropdownExposed by remember { mutableStateOf(false) }
	val error = if (selectedType == null) {
		"Choose a Type"
	} else {
		""
	}
	Column {
		ExposedDropdownMenuBox(
			expanded = dropdownExposed,
			onExpandedChange = { dropdownExposed = !dropdownExposed }
		) {
			OutlinedTextField(
				modifier = Modifier.menuAnchor(),
				value = selectedType ?: "",
				label = { Text("Exercise...") },
				onValueChange = {},
				isError = (selectedType == null)
			)
			ExposedDropdownMenu(
				expanded = dropdownExposed,
				onDismissRequest = { dropdownExposed = false }) {
				for (i in exercises) {
					DropdownMenuItem(
						text = { Text(i.value) },
						onClick = { setExercise(i.key) })
				}
			}
		}
		Text(error, color = Color.Red)
	}
}