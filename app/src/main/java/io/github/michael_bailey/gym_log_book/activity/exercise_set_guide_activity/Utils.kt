package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object Utils {
	fun navigateAskExtraSet(nav: NavHostController) {
		nav.navigate(ExerciseSetGuideActivityPage.AskExtraSet.route) {
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

	fun navigatePause(nav: NavHostController) {
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