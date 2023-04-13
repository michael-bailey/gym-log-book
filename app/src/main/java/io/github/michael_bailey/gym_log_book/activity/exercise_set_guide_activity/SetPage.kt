package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.lib.Validator
import io.github.michael_bailey.gym_log_book.lib.componenets.ValidatorTextField
import kotlinx.coroutines.delay
import kotlin.time.Duration

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPage(
	nav: NavHostController,
	vm: SetGuideViewModel,
) {

	val activity = LocalContext.current as Activity

	LaunchedEffect(Unit) {
		delay(Duration.parse("3s"))
		(activity.application as App).appNotificationManager.cancelTimerNotification()
	}

	val weight = vm.nextWeight.observeAsState("")
	val reps = vm.nextReps.observeAsState("")
	val set = vm.setNumber.observeAsState(1)

	val enabled = vm.submitSetEnabled.observeAsState(initial = false)

	val test = vm.weight.observeAsState(0.0)
	val test1 = test.value

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
			enabled = enabled.value,
			onClick = {
				vm.finalise()
				if (set.value >= 3) {
					Utils.navigateAskExtraSet(nav)
				} else {
					Utils.navigatePause(nav)
				}
				vm.completeSet()
			}
		) {
			Text(text = "Submit set ${vm.setNumber.value}", fontSize = 18.sp)
		}
	}
}

