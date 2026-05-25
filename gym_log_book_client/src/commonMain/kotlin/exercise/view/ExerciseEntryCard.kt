@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun ExerciseEntryCard(
	modifier: Modifier,
	exerciseEntry: ExerciseEntryView
) {
	Card(
		modifier = modifier
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
	val exerciseEntry = ExerciseEntryView(
		id = Uuid.random(),
		date = now,
		exerciseTypeId = Uuid.random(),
		exerciseTypeName = "Test Type",
		setNumber = 3,
		weight = 12.25,
		reps = 8,
	)
	ExerciseEntryCard(
		modifier = Modifier.fillMaxWidth(),
		exerciseEntry = exerciseEntry,
	)
}
