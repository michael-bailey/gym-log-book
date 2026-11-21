@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_type

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
	val selectionState = remember { DevLocalExerciseTypeSelectionState() }

	Column(
		modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
	) {
		DevLocalTypeSelectionToolbar(
			selectionState = selectionState,
			showNewTypeDialogue = { shownModal = ModalState.NewExerciseType },
			onDelete = { ids -> shownModal = ModalState.DeleteExerciseType(ids) }
		)
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
					showCheckBox = selectionState.isSelectionModeShown,
					isChecked = it.id in selectionState.selectedTypeIds,
					onCheckedChange = { id ->
						if (selectionState.selectedTypeIds.contains(id)) selectionState.selectedTypeIds.remove(id)
						else selectionState.selectedTypeIds.add(id)
					},
				)
			}
		}
	}

	Modal(selectionState, shownModal, typeList, viewModel) {
		shownModal = it
	}
}

@Composable
private fun Modal(
	selectionState: DevLocalExerciseTypeSelectionState,
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
					selectionState.disableSelection()
				},
				onDismiss = { updateModalState(ModalState.None) }
			)
		}

		ModalState.NewExerciseType -> NewExerciseTypeDialog(
			onDismissRequest = { updateModalState(ModalState.None) },
			onSave = { name, eClass ->
				viewModel.addExerciseType(name, eClass)
				updateModalState(ModalState.None)
				selectionState.disableSelection()
			},
		)

		ModalState.None -> {}
	}
}


sealed interface ModalState {
	data object None : ModalState
	data object NewExerciseType : ModalState
	data class DeleteExerciseType(val ids: List<Uuid>) : ModalState
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
