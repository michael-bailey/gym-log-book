package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.SetGuideViewModelV2
import org.british_information_technologies.gym_log_book.lib.componenets.text_fields.OutlinedFloatField
import org.british_information_technologies.gym_log_book.lib.componenets.text_fields.ValidatorTextField
import org.british_information_technologies.gym_log_book.lib.validation.Validator


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SetPage(
	vm: SetGuideViewModelV2,
) {
	LaunchedEffect(Unit) {
		vm.resetTimer()
	}

	val setNumber by vm.exerciseSet.observeAsState(initial = 0)
	val weight by vm.weight.observeAsState("")
	val reps by vm.reps.observeAsState("")

	val pastEntries by vm.pastEntries.observeAsState(listOf())

	val enabled by vm.canSubmit.observeAsState(initial = false)

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Next Set", fontSize = 36.sp)
		Column {
			if (pastEntries.isEmpty()) {
				Text(text = "No previous entries", fontSize = 20.sp)
			} else {
				Text(text = "Previous entries", fontSize = 20.sp)
				for (entry in pastEntries) {
					Text(text = "${entry.weight}Kg at ${entry.reps} reps")
				}
			}
		}
		Column(
			modifier = Modifier
				.fillMaxWidth(0.9f)
				.wrapContentHeight(),
			verticalArrangement = Arrangement.SpaceAround,
			horizontalAlignment = Alignment.CenterHorizontally
		) {

			OutlinedFloatField(
				value = weight,
				onChanged = vm::setWeight,
				placeholder = "Weight",
			)

			ValidatorTextField(
				state = weight,
				validator = Validator.FloatValidator(),
				placeholder = "Weight",
				onChange = vm::setWeight
			)

			ValidatorTextField(
				state = reps,
				validator = Validator.NumberValidator(true),
				placeholder = "Reps",
				onChange = vm::setReps
			)
		}
		Button(
			enabled = enabled,
			onClick = { vm.saveSet() }
		) {
			Text(text = "Submit set $setNumber", fontSize = 18.sp)
		}
	}
}

