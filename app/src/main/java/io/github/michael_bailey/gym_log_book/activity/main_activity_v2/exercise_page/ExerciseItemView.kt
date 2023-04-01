package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.exercise_page

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import java.time.LocalDate
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ExerciseItemView(item: ExerciseItem) {

	val squareSize = 250.dp

	val swipeableState = rememberSwipeableState(0)
	val sizePx = with(LocalDensity.current) { squareSize.toPx() }
	val anchors =
		mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

	Box(
		Modifier.swipeable(
			state = swipeableState,
			anchors = anchors,
			thresholds = { _, _ -> FractionalThreshold(0.3f) },
			orientation = Orientation.Horizontal
		)
	) {
		Card(
			Modifier.offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
		) {
			Column(
				Modifier
					.fillMaxWidth()
					.wrapContentHeight()
					.padding(16.dp)
			) {
				Text(
					text = "${item.exercise}",
					fontSize = 14.sp,
					fontWeight = FontWeight(400)
				)
				Row(
					Modifier
						.wrapContentHeight()
						.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Text(
						text = "Set: ${item.setNumber}",
						fontSize = 22.sp,
						fontWeight = FontWeight(500)
					)
					Text(
						text = "${item.weight} KG",
						fontSize = 22.sp,
						fontWeight = FontWeight(500)
					)
					Text(
						text = "${item.reps} reps",
						fontSize = 22.sp,
						fontWeight = FontWeight(500)
					)
				}
			}
		}
	}
}

@Preview
@Composable
fun ExerciseItemViewPreview() {
	ExerciseItemView(
		ExerciseItem(
			1,
			LocalDate.now(),
			"Test exercise",
			1,
			70.0,
			8
		)
	)
}