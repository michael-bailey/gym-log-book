package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.gym_log_book.extension.any.log
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class ExerciseSetGuideActivity : ComponentActivity() {

	val items = listOf(
		ExerciseSetGuideActivityPage.Start,
		ExerciseSetGuideActivityPage.Set,
		ExerciseSetGuideActivityPage.Pause,
	)

	val vm: SetGuideViewModelV2 by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		log("${intent.data}")

		setContent {
			Gym_Log_BookTheme(colourNavBar = false) {
				Main(vm = vm, this::finish)
			}
		}
	}

	override fun onRestart() {
		super.onRestart()
		log("[onRestart]: canceling notification")
		vm.cancelTimerNotification()
	}
}
