@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun DevExerciseEntryListCard(
	modifier: Modifier = Modifier,
	exerciseEntry: ExerciseEntry,
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
			if (showCheckBox) {
				Checkbox(
					checked = isChecked,
					onCheckedChange = { onCheckedChange(exerciseEntry.id) }
				)
			}
			Icon(
				modifier = Modifier.padding(end = 12.dp),
				imageVector = Icons.Filled.Info,
				contentDescription = ""
			)
			Column(
				horizontalAlignment = Alignment.End
			) {
				Text(text = "Id: ")
				Text(text = "Type Id: ")
				Text(text = "Type Name: ")
				Text(text = "Set Number: ")
				Text(text = "Reps: ")
				Text(text = "Weight: ")
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
			}

		}
	}
}

@Preview
@Composable
fun DevExerciseEntryListCard_Preview() {
	DevExerciseEntryListCard(
		exerciseEntry = ExerciseEntry(
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