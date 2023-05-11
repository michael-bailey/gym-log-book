package io.github.michael_bailey.gym_log_book.activity.debug_settings_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

class DebugSettingsActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val vm: DebugSettingsViewModel by viewModels {
			DebugSettingsViewModelFactory(application = application as App)
		}

		setContent {
			val isDebugEnabled = vm.isDebugEnabled.observeAsState(false)
			Gym_Log_BookTheme(colourNavBar = isDebugEnabled.value) {
				// A surface container using the 'background' color from the theme

				Main(vm)

			}
		}
	}
}

