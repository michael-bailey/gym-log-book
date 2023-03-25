package io.github.michael_bailey.gym_log_book.exercise_set_guide_activity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPage(
	nav: NavHostController,
	vm: SetGuideViewModel,
	modifier: Modifier? = null
) {

	val weight = vm.nextWeight.collectAsState()
	val reps = vm.nextReps.collectAsState()

	val context = LocalContext.current

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Next Set", fontSize = 36.sp)
		Column(
			modifier = Modifier
				.fillMaxWidth(0.9f)
				.fillMaxHeight(0.2f),
			verticalArrangement = Arrangement.SpaceAround,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			OutlinedTextField(
				value = weight.value.let {
					if (it == 0) {
						""
					} else {
						it.toString()
					}
				},
				onValueChange = vm::setWeight,
				placeholder = { Text(text = "Enter Weight") },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				singleLine = true
			)

			OutlinedTextField(
				value = reps.value.let {
					if (it == 0) {
						""
					} else {
						it.toString()
					}
				},
				onValueChange = vm::setReps,
				placeholder = { Text(text = "Enter Reps") },
				singleLine = true,
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
			)
		}
		Button(onClick = {
			vm.finalise(context)
			if (vm.setNumber.value >= 3) {
				Utils.navigateAskExtraSet(nav)
			} else {
				Utils.navigatePause(nav)
			}
			vm.completeSet()
		}) {
			Text(text = "Submit set ${vm.setNumber.value}", fontSize = 18.sp)
		}

	}
}

object Utils {
	inline fun navigateAskExtraSet(nav: NavHostController) {
		nav.navigate(ExerciseSetGuideActivityPage.AskExtraSet.route) {
			popUpTo(nav.graph.findStartDestination().id) {
				saveState = true
			}
			// Avoid multiple copies of the same destination when
			// reselecting the same item
			launchSingleTop = true
			// Restore state when reselecting a previously selected item
			restoreState = true
		}
	}

	inline fun navigatePause(nav: NavHostController) {
		nav.navigate(ExerciseSetGuideActivityPage.Pause.route) {
			popUpTo(nav.graph.findStartDestination().id) {
				saveState = true
			}
			// Avoid multiple copies of the same destination when
			// reselecting the same item
			launchSingleTop = true
			// Restore state when reselecting a previously selected item
			restoreState = true
		}
	}
}