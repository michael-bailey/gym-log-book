package net.michael_bailey.gym_log_book.client.window.developer.local_type

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass

@Composable
internal fun NewExerciseTypeDialog(
	onDismissRequest: () -> Unit,
	onSave: (String, EquipmentClass) -> Unit,
) {
	Dialog(
		onDismissRequest = onDismissRequest,
		properties = DialogProperties(),
	) {
		val (name, setName) = remember { mutableStateOf("") }
		val (selectedEquipmentClass, setSelectedEquipmentClass) = remember {
			mutableStateOf<EquipmentClass>(EquipmentClass.None)
		}
		val (isTypeClassDropdownOpen, setTypeClassDropdownOpen) = remember { mutableStateOf(false) }
		val trimmedName = name.trim()

		Card {
			Column(
				modifier = Modifier.padding(24.dp),
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				Text(
					text = "Create exercise type",
					style = MaterialTheme.typography.headlineSmall,
				)
				OutlinedTextField(
					value = name,
					onValueChange = setName,
					label = { Text("Name") },
					singleLine = true,
					modifier = Modifier.fillMaxWidth(),
				)
				Box {
					OutlinedButton(
						onClick = { setTypeClassDropdownOpen(true) }) {
						Text("Equipment: ${selectedEquipmentClass.displayName}")
					}
					DropdownMenu(
						expanded = isTypeClassDropdownOpen,
						onDismissRequest = { setTypeClassDropdownOpen(false) },
					) {
						exerciseTypeEquipmentClasses.forEach { equipmentClass ->
							DropdownMenuItem(text = { Text(equipmentClass.displayName) }, onClick = {
								setSelectedEquipmentClass(equipmentClass)
								setTypeClassDropdownOpen(false)
							})
						}
					}
				}
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.End,
				) {
					TextButton(onClick = onDismissRequest) {
						Text("Cancel")
					}
					Button(
						onClick = {
							onSave(trimmedName, selectedEquipmentClass)
						},
						enabled = trimmedName.isNotEmpty(),
					) {
						Text("Save")
					}
				}
			}
		}
	}
}

private val exerciseTypeEquipmentClasses = listOf(
	EquipmentClass.Machine,
	EquipmentClass.UserWeightMachine,
	EquipmentClass.Calisthenics,
	EquipmentClass.FreeWeight,
	EquipmentClass.None,
)
