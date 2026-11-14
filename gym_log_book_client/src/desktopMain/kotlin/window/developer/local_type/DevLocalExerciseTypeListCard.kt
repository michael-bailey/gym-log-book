@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_type

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.michael_bailey.gym_log_book.client.sync.SyncStatus
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun DevLocalExerciseTypeCard(
	id: Uuid,
	name: String,
	typeClass: String,
	syncStatus: SyncStatus,
	isAlive: Boolean,
	showCheckBox: Boolean,
	isChecked: Boolean,
	onCheckedChange: (Uuid) -> Unit,
) {
	Card(
		modifier = Modifier
			.fillMaxWidth(),
		colors = CardDefaults.cardColors(
			containerColor = if (isChecked) {
				MaterialTheme.colorScheme.secondaryContainer
			} else {
				MaterialTheme.colorScheme.surfaceContainer
			}
		),
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable(enabled = showCheckBox) { onCheckedChange(id) }
				.padding(12.dp)
		) {
			if (showCheckBox) {
				Checkbox(
					checked = isChecked,
					onCheckedChange = { onCheckedChange(id) }
				)
			}
			Column {
				Text("Id: $id")
				Text("Name: $name")
				Text("Type Class: $typeClass")
				Text("Sync Status: ${getSyncStatus(syncStatus)}")
				Text("is alive: $isAlive")
			}
		}
	}
}

fun getSyncStatus(syncStatus: SyncStatus) = when (syncStatus) {
	SyncStatus.SavePending -> "SavePending"
	SyncStatus.DeletePending -> "DeletePending"
	SyncStatus.Synchronised -> "Synchronised"
}
