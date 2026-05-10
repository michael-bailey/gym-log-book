@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
	exerciseEntry: ExerciseEntry
) {
	Card {
		Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
			Icon(modifier = Modifier.padding(end = 12.dp), imageVector = Icons.Filled.Info, contentDescription = "")
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
		),
	)
}