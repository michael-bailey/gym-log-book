package io.github.michael_bailey.gym_log_book.activity.main_activity

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val vm: MainActivityViewModel by viewModels()

		ActivityCompat.requestPermissions(
			this,
			arrayOf(Manifest.permission.POST_NOTIFICATIONS),
			100
		)

		setContent {
			Gym_Log_BookTheme(colourNavBar = true) {
				// A surface container using the 'background' color from the theme
				Main(vm = vm)
			}
		}
	}
}

