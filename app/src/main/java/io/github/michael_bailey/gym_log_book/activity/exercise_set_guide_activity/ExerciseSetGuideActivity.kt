package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.extension.any.log
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

	override fun onPause() {
		super.onPause()
		log("[onPause]")
	}

	override fun onResume() {
		super.onResume()
		log("[onResume]")
	}

	override fun onLowMemory() {
		log("[onLowMemory]")
		super.onLowMemory()
	}

	override fun onStart() {
		super.onStart()
		log("[onStart]")
	}

	override fun onStop() {
		super.onStop()
		log("[onStop]")
	}

	override fun onDestroy() {
		super.onDestroy()
		log("[onDestroy]")
	}

	override fun onRestart() {
		super.onRestart()
		log("[onRestart]: canceling notification")
		(application as App).appNotificationManager.cancelTimerNotification()
	}
}
