@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_type

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
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun DevLocalExerciseTypeTabPage(
	viewModel: DevLocalExerciseTypeTabPageViewModel = koinInject(),
) {

	val typeList by viewModel.exerciseTypeList.collectAsState(initial = emptyList())

	var shownModal by remember { mutableStateOf<ModalState>(ModalState.None) }
	var isSelectionModeShown by remember { mutableStateOf(false) }
	val selectedTypeIds = remember { mutableStateListOf<Uuid>() }
	val selectedCount = selectedTypeIds.size

	Column(
		modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
	) {
		SelectionToolbar(
			isSelectionModeShown,
			selectedCount,
			showNewTypeDialogue = { shownModal = ModalState.NewExerciseType },
			hideSelectionMode = { isSelectionModeShown = false },
			onShowSelect = { isSelectionModeShown = true },
			// here
			onDelete = { shownModal = ModalState.DeleteExerciseType(selectedTypeIds.toList()) })
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
			contentPadding = PaddingValues(8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(
				items = typeList,
				key = { it.id },
			) {
				DevLocalExerciseTypeCard(
					id = it.id,
					name = it.name,
					typeClass = it.equipmentClass.displayName,
					syncStatus = it.syncStatus,
					isAlive = it.isAlive,
					showCheckBox = isSelectionModeShown,
					isChecked = it.id in selectedTypeIds,
					onCheckedChange = { id ->
						if (selectedTypeIds.contains(id)) selectedTypeIds.remove(id)
						else selectedTypeIds.add(id)
					},
				)
			}
		}
	}

	Modal(shownModal, typeList, viewModel) {
		shownModal = it
	}
}

@Composable
private fun Modal(
	shownModal: ModalState,
	typeList: List<ExerciseTypeEntity>,
	viewModel: DevLocalExerciseTypeTabPageViewModel,
	updateModalState: (ModalState) -> Unit,
) {
	when (shownModal) {
		is ModalState.DeleteExerciseType -> {
			DevLocalExerciseTypeDeleteDialogue(
				removeTypes = shownModal.ids,
				exerciseTypes = typeList,
				onDelete = {
					viewModel.deleteSelectedExerciseTypes(shownModal.ids, it)
					updateModalState(ModalState.None)
				},
				onDismiss = { updateModalState(ModalState.None) }
			)
		}

		ModalState.NewExerciseType -> NewExerciseTypeDialog(
			onDismissRequest = { updateModalState(ModalState.None) },
			onSave = viewModel::addExerciseType,
		)

		ModalState.None -> {}
	}
}


sealed interface ModalState {
	data object None : ModalState
	data object NewExerciseType : ModalState
	data class DeleteExerciseType(val ids: List<Uuid>) : ModalState
}

@Composable
private fun SelectionToolbar(
	isSelectionModeShown: Boolean,
	selectedCount: Int,
	showNewTypeDialogue: () -> Unit,
	hideSelectionMode: () -> Unit,
	onShowSelect: () -> Unit,
	onDelete: () -> Unit,
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
			Button(onClick = showNewTypeDialogue) {
				Icon(Icons.Rounded.Add, contentDescription = null)
				Text("Add Type")
			}
			if (isSelectionModeShown) {
				OutlinedButton(onClick = hideSelectionMode) {
					Text("Cancel")
				}
				Button(
					onClick = onDelete,
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
				OutlinedButton(onClick = onShowSelect) {
					Text("Select")
				}
			}
		}
	}
}


val EquipmentClass.displayName: String
	get() = when (this) {
		EquipmentClass.FreeWeight -> "Free Weight"
		EquipmentClass.Machine -> "Machine"
		EquipmentClass.None -> "Please Select"
		EquipmentClass.UserWeightMachine -> "User Weight Machine"
		EquipmentClass.Calisthenics -> "Calisthenics"
		is EquipmentClass.Undefined -> "undefined name:${this.text}"
	}
