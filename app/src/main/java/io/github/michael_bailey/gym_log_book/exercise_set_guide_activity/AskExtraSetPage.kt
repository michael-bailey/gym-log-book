package io.github.michael_bailey.gym_log_book.exercise_set_guide_activity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AskExtraSetPage(nav: NavHostController, onFinished: () -> Unit) {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Next Set", fontSize = 36.sp)
		Row(
			Modifier.fillMaxWidth(0.9f),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			Button(onClick = {
				onFinished()
			}) {
				Text(text = "Stop", fontSize = 18.sp)
			}
			Button(onClick = {
				Util.navigate(nav)
			}) {
				Text(text = "One More...", fontSize = 18.sp)
			}
		}
	}
}

object Util {
	fun navigate(nav: NavHostController) {
		nav.navigate(ExerciseSetGuideActivityPage.Pause.route) {
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
}
