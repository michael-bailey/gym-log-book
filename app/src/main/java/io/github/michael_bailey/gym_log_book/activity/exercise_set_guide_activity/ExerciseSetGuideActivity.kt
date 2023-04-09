package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

class ExerciseSetGuideActivity : ComponentActivity() {

	val items = listOf(
		ExerciseSetGuideActivityPage.Start,
		ExerciseSetGuideActivityPage.Set,
		ExerciseSetGuideActivityPage.Pause,
	)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val vm: SetGuideViewModel by viewModels(
			factoryProducer = {
				ExerciseSetGuideViewModelFactory(
					applicationContext as App,
				)
			}
		)

		setContent {
			Gym_Log_BookTheme(colourNavBar = false) {
				Main(vm = vm, this::finish)
			}
		}
	}
}
