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
				.fillMaxWidth(),
			shape = RoundedCornerShape(20.dp),
			color = MaterialTheme.colorScheme.surfaceDim,
		) {

			LazyColumn(
				Modifier.background(Color.Transparent),
				contentPadding = PaddingValues(20.dp)
			) {
				items(
					items = exerciseMap.entries.toList(),
				) { item ->

					val textFun = @Composable {
						Text(
							modifier = Modifier
								.padding(PaddingValues(8.dp))
								.fillParentMaxWidth()
								.clickable { vm.setExerciseType(item.key) },
							text = item.value
						)
					}


					if (item.value == currentExercise) {
						Surface(
							shape = RoundedCornerShape(12.dp)
						) {
							textFun()
						}
					} else {
						textFun()
					}
				}
			}
		}

		Row(
			modifier = Modifier.padding(20.dp),
			horizontalArrangement = Arrangement.End
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



