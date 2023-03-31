package io.github.michael_bailey.gym_log_book.lib.componenets

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CardWithSwipeActions(
	actions: @Composable RowScope.() -> Unit,
	content: @Composable () -> Unit
) {

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
			content()
		}
		Row(content = actions)
	}
}