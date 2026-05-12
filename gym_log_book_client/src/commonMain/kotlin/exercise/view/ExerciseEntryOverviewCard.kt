@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun ExerciseEntryOverviewCard(
	modifier: Modifier = Modifier,
	id: String,
	name: String,
	date: LocalDateTime
) = Card(
	modifier = modifier
) {
	Column(
		modifier = Modifier.padding(12.dp),
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		Text(text = id)
		Text(text = name)
		Text(text = date.toString())
	}

}

@Preview
@Composable
fun ExerciseEntryOverviewCard_Preview() {
	ExerciseEntryOverviewCard(
		id = Uuid.random().toString(),
		name = Uuid.random().toString(),
		date = Clock.System.now().toLocalDateTime(TimeZone.UTC),
	)
}