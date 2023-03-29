package io.github.michael_bailey.gym_log_book.activity.debug_settings_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.extension.component_activity.isDebugEnabled
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

class DebugSettingsActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val vm: DebugSettingsViewModel by viewModels {
			DebugSettingsViewModelFactory(application = application as App)
		}

		val debug = application.isDebugEnabled()

		setContent {
			Gym_Log_BookTheme(colourNavBar = debug) {
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

