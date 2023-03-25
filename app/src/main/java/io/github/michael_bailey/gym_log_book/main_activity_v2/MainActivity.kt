package io.github.michael_bailey.gym_log_book.main_activity_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val vm: MainActivityV2ViewModel by viewModels()
		setContent {
			Gym_Log_BookTheme(colourNavBar = true) {
				// A surface container using the 'background' color from the theme
				Main(vm = vm)
			}
		}
	}
}

