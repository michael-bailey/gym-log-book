package net.michael_bailey.gym_log_book.client.window.developer.local_entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DevLocalEntrySelectionBar(
	selectionState: DevLocalExerciseEntrySelectionState,
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
				text = if (selectionState.isSelectionModeShown) {
					"${selectionState.selectedCount} selected"
				} else {
					"Entry page"
				}
			)
			Button(onClick = onCreate) {
				Icon(Icons.Rounded.Add, contentDescription = null)
				Text("Add Type")
			}
			if (selectionState.isSelectionModeShown) {
				OutlinedButton(onClick = selectionState::disableSelection) {
					Text("Cancel")
				}
				Button(
					onClick = onDelete,
					enabled = selectionState.deleteEnabled,
					colors = ButtonDefaults.buttonColors(
						containerColor = MaterialTheme.colorScheme.error,
						contentColor = MaterialTheme.colorScheme.onError,
					),
				) {
					Icon(Icons.Rounded.Delete, contentDescription = null)
					Text("Delete")
				}
			} else {
				OutlinedButton(onClick = selectionState::enableSelection) {
					Text("Select")
				}
			}
		}
	}
}