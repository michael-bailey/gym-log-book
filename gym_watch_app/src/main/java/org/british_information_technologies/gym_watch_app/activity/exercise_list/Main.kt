package org.british_information_technologies.gym_watch_app.activity.exercise_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.myapplication.R
import org.british_information_technologies.gym_watch_app.theme.Gym_Log_BookTheme

@Composable
fun Main(greetingName: String) {
	Gym_Log_BookTheme {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colors.background),
			contentAlignment = Alignment.Center
		) {
			TimeText()
			Text(
				modifier = Modifier.fillMaxWidth(),
				textAlign = TextAlign.Center,
				color = MaterialTheme.colors.primary,
				text = stringResource(R.string.hello_world, greetingName)
			)
		}
	}
}