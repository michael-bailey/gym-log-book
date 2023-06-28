package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.gym_log_book.lib.view_model.ListViewModel
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class MainActivityV2 : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val vm: MainActivityV2ViewModel by viewModels()

		ActivityCompat.requestPermissions(
			this,
			arrayOf<String>(Manifest.permission.POST_NOTIFICATIONS),
			100
		)

		val weightVM: ListViewModel by viewModels()

		setContent {
			Gym_Log_BookTheme(colourNavBar = true) {
				// A surface container using the 'background' color from the theme
				Main(vm = vm)
			}
		}
	}
}

