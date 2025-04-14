package org.british_information_technologies.gym_log_book.activity.main_activity.home_page.home_tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeTabView(
	viewModel: HomeTabViewModel = hiltViewModel()
) {

	val lastGymSessionDuration by viewModel.lastGymVisit.observeAsState()
	val lastSessionsExercises by viewModel.lastSessionsExercises.observeAsState(
		initial = listOf()
	)

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 16.dp)
			.verticalScroll(rememberScrollState()),
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		Card(
			modifier = Modifier
				.fillMaxWidth()
		) {
			Text(
				modifier = Modifier.padding(16.dp),
				text = "Last visit was $lastGymSessionDuration",
				fontSize = 24.sp,
				fontWeight = FontWeight.SemiBold
			)
		}

		Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
			Card(modifier = Modifier.weight(1f)) {
				Column(modifier = Modifier.padding(16.dp)) {
					for (exercise in lastSessionsExercises ?: listOf()) {
						Text(text = exercise)
					}
				}
			}
			Card(modifier = Modifier.weight(1f)) {
				Text(modifier = Modifier.padding(16.dp), text = "Side two")
			}
		}
	}
}
