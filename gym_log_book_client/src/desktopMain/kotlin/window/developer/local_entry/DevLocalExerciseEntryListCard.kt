@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.lcoal_entry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.sync.SyncStatus
import net.michael_bailey.gym_log_book.client.window.developer.entry.DevExerciseEntryListCard
import net.michael_bailey.gym_log_book.client.window.developer.local_entry.DevLocalExerciseEntryTabPageViewModel.LocalEntryView
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun DevLocalExerciseEntryListCard(
	modifier: Modifier = Modifier,
	exerciseEntry: LocalEntryView,
	// todo: Move this into separate functionality, maybe make a state container for it?
	showCheckBox: Boolean = false,
	isChecked: Boolean = false,
	onCheckedChange: (Uuid) -> Unit = {},
) {
	Card(
		modifier = modifier.fillMaxWidth(),
		colors = CardDefaults.cardColors(
			containerColor = if (isChecked) {
				MaterialTheme.colorScheme.secondaryContainer
			} else {
				MaterialTheme.colorScheme.surfaceContainer
			}
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable(enabled = showCheckBox) { onCheckedChange(exerciseEntry.id) }
				.padding(12.dp),
		) {
			Icon(
				modifier = Modifier.padding(end = 12.dp),
				imageVector = Icons.Filled.Info,
				contentDescription = ""
			)

			Row {
				Icon(
					modifier = Modifier.padding(end = 12.dp),
					imageVector = getStatusIcon(exerciseEntry.syncStatus),
					contentDescription = exerciseEntry.syncStatusLabel
				)
				Text(exerciseEntry.syncStatusLabel)
			}

		}
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable(enabled = showCheckBox) { onCheckedChange(exerciseEntry.id) }
				.padding(12.dp),
		) {
			if (showCheckBox) {
				Checkbox(
					checked = isChecked,
					onCheckedChange = { onCheckedChange(exerciseEntry.id) }
				)
			}
			Column(
				horizontalAlignment = Alignment.End
			) {
				Text(text = "Id: ")
				Text(text = "Type Id: ")
				Text(text = "Type Name: ")
				Text(text = "Set Number: ")
				Text(text = "Reps: ")
				Text(text = "Weight: ")
				Text(text = "Sync Status: ")
				Text(text = "Is Alive: ")
			}
			Column(
				horizontalAlignment = Alignment.Start
			) {
				Text(text = "${exerciseEntry.id}")
				Text(text = "${exerciseEntry.exerciseTypeId}")
				Text(text = exerciseEntry.exerciseTypeName)
				Text(text = "${exerciseEntry.setNumber}")
				Text(text = "${exerciseEntry.reps}")
				Text(text = "${exerciseEntry.weight}")
				Text(text = exerciseEntry.syncStatusLabel)
				Text(text = "${exerciseEntry.isAlive}")
			}

		}
	}
}

@Composable
fun getStatusIcon(syncStatus: SyncStatus) = when (syncStatus) {
	SyncStatus.SavePending -> Icons.Filled.Sync
	SyncStatus.DeletePending -> Icons.Filled.Sync
	SyncStatus.Synchronised -> Icons.Filled.CheckCircle
}

@Preview
@Composable
fun DevExerciseEntryListCard_Preview() {
	DevExerciseEntryListCard(
		exerciseEntry = ExerciseEntryView(
			id = Uuid.random(),
			date = Clock.System.now().toLocalDateTime(TimeZone.UTC),
			exerciseTypeId = Uuid.random(),
			exerciseTypeName = "Random Type",
			setNumber = 3,
			weight = 27.25,
			reps = 12
		)
	)
}
