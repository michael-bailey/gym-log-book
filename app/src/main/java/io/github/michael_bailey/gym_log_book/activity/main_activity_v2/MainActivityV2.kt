package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.extension.any.log
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

class MainActivityV2 : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val vm: MainActivityV2ViewModel by viewModels {
			MainActivityV2ViewModelFactory(application = application as App)
		}

		setContent {
			Gym_Log_BookTheme(colourNavBar = true) {
				// A surface container using the 'background' color from the theme
				Main(vm = vm)
			}
		}
	}

	override fun onLowMemory() {
		log("onLowMemory")
	}

	override fun onResume() {
		super.onResume()
		log("onResume")
		val vm: MainActivityV2ViewModel by viewModels {
			MainActivityV2ViewModelFactory(application = application as App)
		}
		vm.forceUpdate()
	}
}

