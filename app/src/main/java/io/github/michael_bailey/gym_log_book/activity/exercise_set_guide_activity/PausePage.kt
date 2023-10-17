package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


@Composable
fun PausePage(
	nav: NavHostController,
	vm: SetGuideViewModelV2,
) {

	val lifecycle = LocalLifecycleOwner.current

	val theme = MaterialTheme.colorScheme
	val timerValue by vm.timerValue.observeAsState(initial = 0)
	var initialLoad by remember { mutableStateOf(true) }

	LaunchedEffect(Unit) {
		vm.startTimer {
			navigate(nav = nav)
		}
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(Modifier.padding(32.dp), contentAlignment = Alignment.Center) {
			Canvas(
				modifier = Modifier
					.fillMaxWidth()
					.aspectRatio(1f)
			) {
				val radius = size.minDimension / 2
				val sweepAngle = 300f * (timerValue.toFloat() / 60.toFloat())
				val startAngle = 120f
				drawArc(
					color = theme.secondary,
					startAngle = startAngle,
					sweepAngle = sweepAngle,
					useCenter = false,
					style = Stroke(width = 16.dp.toPx(), cap = StrokeCap.Round)
				)
			}
			Text("Time Left: $timerValue", fontSize = 36.sp)
		}

		Button(onClick = { navigate(nav) }) {
			Text(text = "Skip Timer")
		}
	}
}

fun navigate(nav: NavHostController) {
	nav.navigate(ExerciseSetGuideActivityPage.Set.route) {
		popUpTo(nav.graph.findStartDestination().id) {
			saveState = true
		}
		// Avoid multiple copies of the same destination when
		// reselecting the same item
		launchSingleTop = true
		// Restore state when reselecting a previously selected item
		restoreState = true
	}
}