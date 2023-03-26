package io.github.michael_bailey.gym_log_book.main_activity_v2

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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ExerciseItemView(item: ExerciseItem) {

	val width = 96.dp
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
					.fillMaxSize(0.9f)
					.padding(16.dp)
			) {
				Text(
					text = "${item.exercise}",
					fontSize = 18.sp,
					fontWeight = FontWeight(500)
				)

				Row(
					Modifier.fillMaxSize(1f),
					horizontalArrangement = Arrangement.SpaceEvenly
				) {
					Text(text = "Set: ${item.setNumber}")
					Text(text = "${item.weight} KG")
				}

				Row(
					Modifier.fillMaxSize(1f),
					horizontalArrangement = Arrangement.SpaceEvenly
				) {
					Text(text = "${item.reps} reps")
				}
			}
		}
	}
}