package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity_v2.components.ExerciseTypeDropdownSelector


@Composable
fun StartPage(
	nav: NavHostController,
	vm: SetGuideViewModelV2,
	modifier: Modifier? = null
) {
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
				nav.navigate(ExerciseSetGuideActivityPage.Set.route) {
					// Pop up to the start destination of the graph to
					// avoid building up a large stack of destinations
					// on the back stack as users select items
					popUpTo(nav.graph.findStartDestination().id) {
						saveState = true
					}
					// Avoid multiple copies of the same destination when
					// reselecting the same item
					launchSingleTop = true
					// Restore state when reselecting a previously selected item
					restoreState = true
				}
			},
			enabled = startEnabled
		) {
			Text(text = "Start", fontSize = 18.sp)
		}
	}
}



