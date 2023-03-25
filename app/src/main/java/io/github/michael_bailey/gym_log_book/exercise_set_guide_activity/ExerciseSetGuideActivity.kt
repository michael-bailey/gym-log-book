package io.github.michael_bailey.gym_log_book.exercise_set_guide_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.navigation.NavHostController

class ExerciseSetGuideActivity : ComponentActivity() {

	val items = listOf(
		ExerciseSetGuideActivityPage.Start,
		ExerciseSetGuideActivityPage.Set,
		ExerciseSetGuideActivityPage.Pause,
	)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val vm: SetGuideViewModel by viewModels()
		setContent {
			Main(vm = vm, this::finish)
		}
	}

	private fun startPageButtonClick(nav: NavHostController) {
		nav.navigate("set_page")
	}

	private fun setPageButtonClick(nav: NavHostController) {
		nav.navigate("pause_page")
	}

	private fun pausePageButtonClick(nav: NavHostController) {
		finish()
	}
}

