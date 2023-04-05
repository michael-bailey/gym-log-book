package io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.extension.any.log
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

class AmendExerciseActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val exerciseNumber = intent.data?.pathSegments?.last()?.toIntOrNull()

		exerciseNumber?.let { log("got exercise number:$it") }

		val vm: AmendExerciseViewModel by viewModels(
			factoryProducer = {
				AmendExerciseViewModelFactory(
					applicationContext as App,
					exerciseId = exerciseNumber
				)
			}
		)

		setContent {
			Gym_Log_BookTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					Main(vm)
				}
			}
		}
	}
}
