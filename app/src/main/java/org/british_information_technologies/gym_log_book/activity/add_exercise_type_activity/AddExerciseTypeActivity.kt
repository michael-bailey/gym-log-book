package org.british_information_technologies.gym_log_book.activity.add_exercise_type_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class AddExerciseTypeActivity : ComponentActivity() {

	val vm: AddExerciseTypeActivityViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

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

