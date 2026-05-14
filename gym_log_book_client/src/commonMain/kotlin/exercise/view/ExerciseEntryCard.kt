@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.home.tabs.entry.IExerciseEntryTabViewModel
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun ExerciseEntryCard(
	exerciseEntry: IExerciseEntryTabViewModel.ExerciseEntryViewData
) {
	Card(
		modifier = Modifier.widthIn(min = 300.dp, max = 500.dp)
	) {
		ExerciseEntryContent(
			type = exerciseEntry.exerciseTypeName,
			createDate = exerciseEntry.date.date.toString(),
			createdTime = exerciseEntry.date.time.toString(),
			setNumber = exerciseEntry.setNumber,
			weight = exerciseEntry.weight,
			reps = exerciseEntry.reps
		)
	}
}

@Preview
@Composable
fun ExerciseEntryCard_Preview() {
	val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
	val exerciseEntry = IExerciseEntryTabViewModel.ExerciseEntryViewData(
		id = Uuid.random(),
		exerciseTypeName = "Test Type",
//		exerciseTypeId = Uuid.random(),
		setNumber = 3,
		weight = 12.25,
		reps = 8,
		date = now,
	)
	ExerciseEntryCard(
		exerciseEntry = exerciseEntry
	)
}
