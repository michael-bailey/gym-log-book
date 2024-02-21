package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivity
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.SetGuideViewModelV2
import org.british_information_technologies.gym_log_book.extension.activity

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AskExtraSetPage(vm: SetGuideViewModelV2) {

	val activity = activity<ExerciseSetGuideActivity>()

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