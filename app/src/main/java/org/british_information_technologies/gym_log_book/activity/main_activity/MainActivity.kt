package org.british_information_technologies.gym_log_book.activity.main_activity

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.british_information_technologies.gym_log_book.activity.onboarding_activity.OnboardingActivityIntentUtils
import org.british_information_technologies.gym_log_book.lib.navigation.NavLocal
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	val vm: MainActivityViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (vm.onboardingComplete.value == false) {
			OnboardingActivityIntentUtils.startActivity(applicationContext)
		}

		ActivityCompat.requestPermissions(
			this,
			arrayOf(
				Manifest.permission.POST_NOTIFICATIONS,
				Manifest.permission.READ_CALENDAR,
				Manifest.permission.WRITE_CALENDAR
			),
			100
		)

		setContent {
			val nav = rememberNavController()
			CompositionLocalProvider(
				NavLocal provides nav
			) {
				Gym_Log_BookTheme(colourNavBar = true) {
					// A surface container using the 'background' color from the theme
					Main(vm = vm)
				}
			}
		}
	}
}

