@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.lcoal_entry

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
import net.michael_bailey.gym_log_book.client.window.developer.local_entry.DevLocalExerciseEntryTabPageViewModel
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun DevLocalExerciseEntryTabPage(
	viewModel: DevLocalExerciseEntryTabPageViewModel = koinInject(),
) {

	val entryList by viewModel.exerciseEntriesList.collectAsState(emptyList())

	var isSelectionModeShown by remember { mutableStateOf(false) }
	var isNewEntryModalShown by remember { mutableStateOf(false) }

	val selectedEntryIds = remember { mutableStateListOf<Uuid>() }
	val selectedCount = selectedEntryIds.size

	Column(
		modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
	) {
		SelectionBar(
			isSelectionModeShown,
			selectedCount,
			{ isSelectionModeShown = !isSelectionModeShown },
			onCreate = { isNewEntryModalShown = true },
			onDelete = {
				viewModel.deleteExerciseEntries(selectedEntryIds.toList())
				selectedEntryIds.clear()
				isSelectionModeShown = false
			}
		)
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
			contentPadding = PaddingValues(8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(
				items = entryList,
				key = { it.id }
			) { entry ->
				DevLocalExerciseEntryListCard(
					modifier = Modifier.fillMaxWidth(),
					exerciseEntry = entry,
					showCheckBox = isSelectionModeShown,
					isChecked = selectedEntryIds.contains(entry.id),
					onCheckedChange = {
						if (selectedEntryIds.contains(it)) selectedEntryIds.remove(it)
						else selectedEntryIds.add(it)
					}
				)
			}
		}
	}

	if (isNewEntryModalShown)
		DevLocalExerciseEntryCreateDialogue(onDismiss = { isNewEntryModalShown = false })
}

@Composable
private fun SelectionBar(
	isSelectionModeShown: Boolean,
	selectedCount: Int,
	toggleSelectionMode: () -> Unit,
	onCreate: () -> Unit,
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
			Button(onClick = onCreate) {
				Icon(Icons.Rounded.Add, contentDescription = null)
				Text("Add Type")
			}
			if (isSelectionModeShown) {
				OutlinedButton(onClick = toggleSelectionMode) {
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
				OutlinedButton(onClick = toggleSelectionMode) {
					Text("Select")
				}
			}
		}
	}
}