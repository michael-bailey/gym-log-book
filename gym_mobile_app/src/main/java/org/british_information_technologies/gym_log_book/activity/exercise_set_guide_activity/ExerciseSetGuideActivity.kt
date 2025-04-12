package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import dagger.hilt.android.AndroidEntryPoint
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page.ExerciseSetGuideActivityPage
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class ExerciseSetGuideActivity : ComponentActivity() {

	val items = listOf(
		ExerciseSetGuideActivityPage.Start,
		ExerciseSetGuideActivityPage.Set,
		ExerciseSetGuideActivityPage.Pause,
		ExerciseSetGuideActivityPage.AskExtraSet,
	)

	val vm: SetGuideViewModelV2 by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		lifecycle.addObserver(vm)


		setContent {
			Gym_Log_BookTheme(colourNavBar = false) {
				Main()
			}
		}
	}

	private fun navigate(nav: NavController, route: String) {
		nav.navigate(route) {
			popUpTo(nav.graph.findStartDestination().id) {
				saveState = true
			}
			launchSingleTop = true
			restoreState = true
		}
	}
}
