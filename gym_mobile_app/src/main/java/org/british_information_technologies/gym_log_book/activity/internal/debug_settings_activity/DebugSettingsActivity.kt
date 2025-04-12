package org.british_information_technologies.gym_log_book.activity.internal.debug_settings_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import dagger.hilt.android.AndroidEntryPoint
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class DebugSettingsActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val vm: DebugSettingsViewModel by viewModels()

		setContent {
			val isDebugEnabled = vm.isDebugEnabled.observeAsState(false)
			Gym_Log_BookTheme(colourNavBar = isDebugEnabled.value) {
				// A surface container using the 'background' color from the theme
				Main(vm)
			}
		}
	}
}

