@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.type

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun DevExerciseTypeTabPage(
	viewModel: DevExerciseTypeTabPageViewModel = koinInject()
) {

	val typeList by viewModel.exerciseTypeList.collectAsState(initial = emptyList())

	var showNewTypeDialogue by viewModel.isNewTypeDialogueShown
	var isSelectionModeShown by viewModel.isSelectionModeShown
	val selectedTypeIds = viewModel.selectedTypeIds
	val selectedCount = selectedTypeIds.size

	Column(
		modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
	) {
		Surface(
			modifier = Modifier.fillMaxWidth().padding(8.dp),
			shape = RoundedCornerShape(16.dp),
			color = MaterialTheme.colorScheme.surfaceDim,
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = if (isSelectionModeShown) {
						"$selectedCount selected"
					} else {
						"Entry page"
					}
				)
				Button(onClick = viewModel::showNewTypeDialogue) {
					Icon(Icons.Rounded.Add, contentDescription = null)
					Text("Add Type")
				}
				if (isSelectionModeShown) {
					OutlinedButton(onClick = viewModel::hideSelectionMode) {
						Text("Cancel")
					}
					Button(
						onClick = viewModel::deleteSelectedExerciseTypes,
						enabled = selectedCount > 0,
						colors = ButtonDefaults.buttonColors(
							containerColor = MaterialTheme.colorScheme.error,
							contentColor = MaterialTheme.colorScheme.onError,
						),
					) {
						Icon(Icons.Rounded.Delete, contentDescription = null)
						Text("Delete")
					}
				} else {
					OutlinedButton(onClick = viewModel::showSelectionMode) {
						Text("Select")
					}
				}
			}
		}
		LazyColumn(Modifier.fillMaxSize()) {
			items(
				items = typeList,
				key = { it.id },
			) {
				DevExerciseTypeCard(
					id = it.id,
					name = it.name,
					typeClass = it.equipmentClass.displayName,
					showCheckBox = isSelectionModeShown,
					isChecked = it.id in selectedTypeIds,
					onCheckedChange = viewModel::toggleExerciseTypeSelection,
				)
			}
		}
	}

	if (showNewTypeDialogue) {
		NewExerciseTypeDialog(
			onDismissRequest = viewModel::hideNewTypeDialogue,
			onSave = viewModel::addExerciseType,
		)
	}
}

@Composable
private fun NewExerciseTypeDialog(
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
	EquipmentClass.UsesUserWeight,
	EquipmentClass.FreeWeight,
	EquipmentClass.None,
)

private val EquipmentClass.displayName: String
	get() = when (this) {
		EquipmentClass.Machine -> "Machine"
		EquipmentClass.UsesUserWeight -> "Uses User Weight"
		EquipmentClass.FreeWeight -> "Free Weight"
		EquipmentClass.None -> "None"
		is EquipmentClass.Undefined -> "Undefined"
	}
