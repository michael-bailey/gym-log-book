package io.github.michael_bailey.gym_log_book.lib.componenets

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CardWithSwipeActions(
	actions: @Composable RowScope.() -> Unit,
	content: @Composable () -> Unit
) {
	var actionsSize by remember { mutableStateOf(IntSize(2, 2)) }
	val squareSize = actionsSize.width.dp
	val sizePx = with(LocalDensity.current) { squareSize.toPx() }
	val anchors = mapOf(
		0f to 0,
		sizePx / 3 to 1,
		(sizePx / 3) * 2 to 2
	) // Maps anchor points (in px) to states

	val swipeableState = rememberSwipeableState(0)


	Box(
		Modifier.swipeable(
			state = swipeableState,
			anchors = anchors,
			thresholds = { _, _ -> FractionalThreshold(0.3f) },
			orientation = Orientation.Horizontal
		)
	) {

		Row(
			Modifier
				.onSizeChanged { actionsSize = it }
				.fillMaxHeight(),
			verticalAlignment = Alignment.CenterVertically,
			content = actions
		)
		Card(
			Modifier.offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
		) {
			content()
		}
	}

}

@Preview
@Composable
fun CardWithSwipeActionsPreview() {
	CardWithSwipeActions({
		Button(onClick = {}) {
			Text("Button")
		}
	}) {
		Text(text = "This is content")
	}
}