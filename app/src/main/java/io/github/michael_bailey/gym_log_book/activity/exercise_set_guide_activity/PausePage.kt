package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@Composable
fun PausePage(
	nav: NavHostController,
	vm: SetGuideViewModel,
	modifier: Modifier? = null
) {
	var count by remember { mutableStateOf(60) }




	LaunchedEffect(Unit) {
		while (count != 0) {
			delay(1.seconds)
			count -= 1
		}
		navigate(nav)
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Time Left: $count", fontSize = 36.sp)
		Button(onClick = { navigate(nav) }) {
			Text(text = "Skip Timer")
		}
	}
}

inline fun navigate(nav: NavHostController) {
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