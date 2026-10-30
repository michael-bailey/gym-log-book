@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun DevExerciseEntryTabPage(
	viewModel: DevExerciseEntryTabPageViewModel = koinInject(),
) {

	val entryList by viewModel.exerciseEntriesList.collectAsState(emptyList())

	var isSelectionModeShown by viewModel.isSelectionModeShown
	val selectedEntryIds = viewModel.selectedEntryIds
	val selectedCount = selectedEntryIds.size

	val isNewEntryModalShown by viewModel.isNewEntryModalShown

	Column(
		modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
	) {
		SelectionBar(isSelectionModeShown, selectedCount, viewModel)
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
			contentPadding = PaddingValues(8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(
				items = entryList,
				key = { it.id }
			) { entry ->
				DevExerciseEntryListCard(
					modifier = Modifier.fillMaxWidth(),
					exerciseEntry = entry,
					showCheckBox = isSelectionModeShown,
					isChecked = selectedEntryIds.contains(entry.id),
					onCheckedChange = viewModel::toggleExerciseTypeSelection
				)
			}
		}
	}

	if (isNewEntryModalShown)
		DevExerciseEntryCreateDialogue(onDismiss = viewModel::closeCreateModal)
}

@Composable
private fun SelectionBar(
	isSelectionModeShown: Boolean,
	selectedCount: Int,
	viewModel: DevExerciseEntryTabPageViewModel
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
			Button(onClick = viewModel::showCreateModal) {
				Icon(Icons.Rounded.Add, contentDescription = null)
				Text("Add Type")
			}
			if (isSelectionModeShown) {
				OutlinedButton(onClick = viewModel::hideSelectionMode) {
					Text("Cancel")
				}
				Button(
					onClick = { TODO() },
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
}