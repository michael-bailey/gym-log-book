package org.british_information_technologies.gym_log_book.activity.amend_exercise_activity_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.gym_log_book.activity.add_exercise_activity.AmendExerciseViewModelV2
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class AmendExerciseActivityV2 : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val viewModel: AmendExerciseViewModelV2 by viewModels()

		setContent {
			Gym_Log_BookTheme {
				Main(vm = viewModel)
			}
		}
	}
}