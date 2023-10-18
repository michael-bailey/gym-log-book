package org.british_information_technologies.gym_log_book.activity.add_weight_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class AddWeightActivity : ComponentActivity() {

	val vm: AddWeightViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Gym_Log_BookTheme {
				Main(vm = vm)
			}
		}
	}
}