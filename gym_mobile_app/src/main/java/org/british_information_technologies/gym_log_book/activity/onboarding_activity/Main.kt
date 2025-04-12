package org.british_information_technologies.gym_log_book.activity.onboarding_activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: OnboardingActivityViewModel) {
	Scaffold(
		content = {
			Column(
				modifier = Modifier
					.padding(it)
					.fillMaxSize(),
				Arrangement.Center,
				Alignment.CenterHorizontally,
				content = {
					Text(text = "Onboading Activity")
				}
			)
		}
	)
}

