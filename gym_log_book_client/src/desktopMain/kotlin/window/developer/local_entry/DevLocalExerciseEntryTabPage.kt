@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.lcoal_entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.michael_bailey.gym_log_book.client.window.developer.local_entry.DevLocalEntrySelectionBar
import net.michael_bailey.gym_log_book.client.window.developer.local_entry.DevLocalExerciseEntrySelectionState
import net.michael_bailey.gym_log_book.client.window.developer.local_entry.DevLocalExerciseEntryTabPageViewModel
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun DevLocalExerciseEntryTabPage(
	viewModel: DevLocalExerciseEntryTabPageViewModel = koinInject(),
) {

	val entryList by viewModel.exerciseEntriesList.collectAsState(emptyList())
	var isNewEntryModalShown by remember { mutableStateOf(false) }

	val selectionState = remember { DevLocalExerciseEntrySelectionState() }

	Column(
		modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
	) {
		DevLocalEntrySelectionBar(
			selectionState = selectionState,
			onCreate = { isNewEntryModalShown = true },
			onDelete = {
				viewModel.deleteExerciseEntries(selectionState.selectedEntryIds.toList())
				selectionState.disableSelection()
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
					showCheckBox = selectionState.isSelectionModeShown,
					isChecked = selectionState.selectedEntryIds.contains(entry.id),
					onCheckedChange = {
						if (selectionState.selectedEntryIds.contains(it)) selectionState.selectedEntryIds.remove(it)
						else selectionState.selectedEntryIds.add(it)
					}
				)
			}
		}
	}

	if (isNewEntryModalShown)
		DevLocalExerciseEntryCreateDialogue(onDismiss = { isNewEntryModalShown = false })
}

