package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivity
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.SetGuideViewModelV2
import org.british_information_technologies.gym_log_book.extension.activity
import org.british_information_technologies.gym_log_book.lib.componenets.ExerciseTypeDropdownSelector

@Composable
fun StartPage(
	vm: SetGuideViewModelV2,
) {

	val activity = activity<ExerciseSetGuideActivity>()
	val vm = activity.vm

	val exerciseMap by vm.exerciseNameMap.observeAsState(initial = mapOf())
	val currentExercise by vm.selectedExerciseType.observeAsState()
	val startEnabled by vm.isStartEnabled.observeAsState(false)

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		ExerciseTypeDropdownSelector(
			exercises = exerciseMap,
			selectedType = currentExercise,
			setExercise = { vm.setExerciseType(it) }
		)
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



