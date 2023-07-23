package io.github.michael_bailey.gym_log_book.activity.onboarding_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {

	val viewModel: OnboardingActivityViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val vm: OnboardingActivityViewModel by viewModels()

		setContent {
			Gym_Log_BookTheme {
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

