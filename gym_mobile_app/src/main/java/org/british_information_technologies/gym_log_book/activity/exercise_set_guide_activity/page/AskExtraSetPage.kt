package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivity
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.SetGuideViewModelV2
import org.british_information_technologies.gym_log_book.extensions.activity

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AskExtraSetPage(vm: SetGuideViewModelV2) {

	val activity = activity<ExerciseSetGuideActivity>()

	val nav = rememberNavController()



	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Next Set", fontSize = 36.sp)
		Row(
			Modifier.fillMaxWidth(0.9f),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			Button(onClick = {
				activity.finish()
			}) {
				Text(text = "Stop", fontSize = 18.sp)
			}
			Button(onClick = {
				vm.goToPause()
			}) {
				Text(text = "One More...", fontSize = 18.sp)
			}
		}
	}
}