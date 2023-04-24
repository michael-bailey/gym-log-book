package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import Spinner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import io.github.michael_bailey.gym_log_book.lib.Validator
import io.github.michael_bailey.gym_log_book.lib.componenets.ValidatorTextField
import io.github.michael_bailey.gym_log_book.lib.gatekeeper.Gatekeeper


@Composable
fun StartPage(
	nav: NavHostController,
	vm: SetGuideViewModel,
	modifier: Modifier? = null
) {

	val currentExercise = vm.exercise.observeAsState("")

	val startEnabled = vm.startEnabled.observeAsState(true)

	val exerciseTypes = vm.exerciseTypes.observeAsState(initial = listOf())

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		if (Gatekeeper.eval("new_exercise_selector")) {
			Spinner(
				list = exerciseTypes.value.map { it.name },
				onSelect = vm::updateCurrentExercise
			)
		} else {
			Text("What Exercise?", fontSize = 36.sp)
			ValidatorTextField(
				state = currentExercise,
				validator = Validator.StringNameValidator as Validator,
				onChange = vm::updateCurrentExercise,
				placeholder = "Exercise Name"
			)
		}
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
			enabled = startEnabled.value
		) {
			Text(text = "Start", fontSize = 18.sp)
		}
	}
}



