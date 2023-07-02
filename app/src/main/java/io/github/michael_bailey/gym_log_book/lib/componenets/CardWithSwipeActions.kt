package io.github.michael_bailey.gym_log_book.lib.componenets

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CardWithSwipeActions(
	modifier: Modifier = Modifier,
	actions: @Composable() (RowScope.() -> Unit),
	content: @Composable () -> Unit,
) {
	val context = rememberCompositionContext()
	val swipeableState = rememberSwipeableState(0)
	val (actionsSize, setActionSize) = remember {
		mutableStateOf(IntSize(2, 2))
	}
	val size = actionsSize.width.toFloat()
//	context.log("sizePx:$size")
	val anchors = mapOf(
		0f to 0,
		size to 1,
	)

	Box(
		Modifier
			.swipeable(
				state = swipeableState,
				anchors = anchors,
				thresholds = { _, _ -> FractionalThreshold(0.3f) },
				orientation = Orientation.Horizontal
			)
			.fillMaxWidth(0.91f),
		contentAlignment = Alignment.CenterStart
	) {
		Row(
			Modifier
				.fillMaxHeight()
				.wrapContentWidth()
				.onSizeChanged(setActionSize),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
			content = actions
		)
		Card(
			Modifier.offset {
				IntOffset(swipeableState.offset.value.roundToInt(), 0)
			}
		) {
			content()
		}
	}

}

@Preview
@Composable
fun CardWithSwipeActionsPreview() {
	CardWithSwipeActions(
		actions = {
			Button(onClick = {}) {
				Text("Button")
			}
		},
		content = {
			Text(text = "This is content")
		}
	)
}