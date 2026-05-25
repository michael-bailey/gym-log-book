package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@Composable
fun ExerciseEntryContent(
	type: String,
	createDate: String,
	createdTime: String,
	setNumber: Int,
	weight: Double,
	reps: Int
) {
	Column(
		Modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.padding(16.dp)
	) {
		Row(
			Modifier
				.wrapContentHeight()
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				text = type,
				fontSize = 14.sp,
				fontWeight = FontWeight(400)
			)
			Row(
				Modifier
					.wrapContentHeight()
					.wrapContentWidth(),
				horizontalArrangement = Arrangement.spacedBy(10.dp)
			) {
				Text(
					text = createDate,
					fontSize = 14.sp,
					fontWeight = FontWeight(400)
				)
				Text(
					text = createdTime,
					fontSize = 14.sp,
					fontWeight = FontWeight(400)
				)
			}
		}
		Row(
			Modifier
				.wrapContentHeight()
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				text = "Set: $setNumber",
				fontSize = 22.sp,
				fontWeight = FontWeight(500)
			)
			Text(
				text = "$weight KG",
				fontSize = 22.sp,
				fontWeight = FontWeight(500)
			)
			Text(
				text = "$reps reps",
				fontSize = 22.sp,
				fontWeight = FontWeight(500)
			)
		}
	}
}

@Preview
@Composable
fun ExerciseEntryContent_Preview() {

	val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
	val time = now.time.toString()
	val date = now.date.toString()

	ExerciseEntryContent(
		type = "Test Type",
		createDate = date,
		createdTime = time,
		setNumber = 3,
		weight = 12.25,
		reps = 8
	)
}