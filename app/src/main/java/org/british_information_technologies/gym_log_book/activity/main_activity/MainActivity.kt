package org.british_information_technologies.gym_log_book.activity.main_activity

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import org.british_information_technologies.gym_log_book.activity.onboarding_activity.OnboardingActivityIntentUtils
import org.british_information_technologies.gym_log_book.repository.ExerciseGroupRepository
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	val vm: MainActivityViewModel by viewModels()

	@Inject
	lateinit var repo: ExerciseGroupRepository

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
			Gym_Log_BookTheme(colourNavBar = true) {
				// A surface container using the 'background' color from the theme
				Main(vm = vm)
			}
		}
	}
}

