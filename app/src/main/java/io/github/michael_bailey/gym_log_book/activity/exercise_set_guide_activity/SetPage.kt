package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.michael_bailey.gym_log_book.lib.Validator
import io.github.michael_bailey.gym_log_book.lib.componenets.ValidatorTextField
import kotlinx.coroutines.delay
import kotlin.time.Duration

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SetPage(
	nav: NavHostController,
	vm: SetGuideViewModelV2,
) {
	LaunchedEffect(Unit) {
		delay(Duration.parse("3s"))
		vm.cancelTimerNotification()
	}

	val setNumber by vm.exerciseSet.observeAsState(initial = 0)
	val weight by vm.weight.observeAsState("")
	val reps by vm.reps.observeAsState("")

	val enabled by vm.canSubmit.observeAsState(initial = false)

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Next Set", fontSize = 36.sp)
		Column(
			modifier = Modifier
				.fillMaxWidth(0.9f)
				.wrapContentHeight(),
			verticalArrangement = Arrangement.SpaceAround,
			horizontalAlignment = Alignment.CenterHorizontally
		) {

			ValidatorTextField(
				state = weight,
				validator = Validator.FloatValidator,
				placeholder = "Weight",
				onChange = vm::setWeight
			)

			ValidatorTextField(
				state = reps,
				validator = Validator.NumberValidator,
				placeholder = "Reps",
				onChange = vm::setReps
			)
		}
		Button(
			enabled = enabled,
			onClick = {
				vm.finalise()
				if (setNumber >= 3) {
					Utils.navigateAskExtraSet(nav)
				} else {
					Utils.navigatePause(nav)
				}
			}
		) {
			Text(text = "Submit set $setNumber", fontSize = 18.sp)
		}
	}
}

