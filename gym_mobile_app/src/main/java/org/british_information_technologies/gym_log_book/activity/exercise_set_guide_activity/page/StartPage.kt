package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.SetGuideViewModelV2

@Composable
fun StartPage(
	vm: SetGuideViewModelV2,
) {

	val exerciseMap by vm.exerciseNameMap.observeAsState(initial = mapOf())
	val currentExercise by vm.selectedExerciseType.observeAsState()
	val startEnabled by vm.isStartEnabled.observeAsState(false)

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		Surface(
			modifier = Modifier
				.weight(1f)
				.fillMaxSize(),
			shape = RoundedCornerShape(20.dp),
			color = MaterialTheme.colorScheme.surfaceDim,
		) {

			LazyColumn(
				Modifier
					.background(Color.Transparent)
					.fillMaxSize(),
			) {
				items(
					items = exerciseMap.entries.toList(),
				) { item ->
					Surface(
						modifier = Modifier
							.clickable { vm.setExerciseType(item.key) }
							.fillMaxWidth(),
						shape = RoundedCornerShape(20.dp),
						color = if (item.value == currentExercise)
							MaterialTheme.colorScheme.surface else Color.Transparent
					) {
						Text(
							modifier = Modifier
								.fillMaxSize()
								.padding(PaddingValues(horizontal = 20.dp, vertical = 8.dp)),
							text = item.value
						)
					}
				}
			}
		}


		Row(
			modifier = Modifier
				.padding(20.dp)
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.Center
		) {
			Button(
				onClick = {
					vm.goToSet()
				},
				enabled = startEnabled
			) {
				Text(text = "Start", fontSize = 18.sp)
			}
		}
	}
}



