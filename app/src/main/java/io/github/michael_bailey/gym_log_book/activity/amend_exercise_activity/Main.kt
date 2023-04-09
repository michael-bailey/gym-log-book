package io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.lib.Validator
import io.github.michael_bailey.gym_log_book.lib.componenets.ValidatorTextField

@Composable
fun Main(vm: AmendExerciseViewModel) {
	val activity = LocalContext.current as Activity
	val numberKeyboard = KeyboardOptions(
		autoCorrect = false,
		keyboardType =
		KeyboardType.Number
	)

	val exerciseId = vm.id.observeAsState("")
	val name = vm.exerciseName.observeAsState("")
	val set = vm.set.observeAsState("")
	val weight = vm.weight.observeAsState("")
	val reps = vm.reps.observeAsState("")

	val nameError = vm.nameError.observeAsState("")
	val setError = vm.setError.observeAsState("")
	val weightError = vm.weightError.observeAsState("")
	val repsError = vm.repsError.observeAsState("")

	val submitable = derivedStateOf {
		nameError.value == "" &&
			setError.value == "" &&
			weightError.value == "" &&
			repsError.value == ""
	}

	Column(
		Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(text = "Amend Exercise", fontSize = 32.sp)
			Text(text = "ID ${exerciseId.value}", fontSize = 24.sp)
		}
		Column {
			ValidatorTextField(
				state = name,
				validator = Validator.StringNameValidator,
				onChange = vm::setName,
				placeholder = "Exercise"
			)

			ValidatorTextField(
				state = set,
				validator = Validator.NumberValidator,
				onChange = vm::setSet,
				placeholder = "Set Number"
			)

			ValidatorTextField(
				state = weight,
				validator = Validator.FloatValidator,
				onChange = vm::setWeight,
				placeholder = "Weight"
			)

			ValidatorTextField(
				state = reps,
				validator = Validator.NumberValidator,
				onChange = vm::setReps,
				placeholder = "Reps"
			)
		}
		Column(Modifier.fillMaxWidth(0.9f)) {
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				Button(
					onClick = { activity.finish() },

					) {
					Text("Cancel")
				}
				Button(
					onClick = { vm.finalise(); activity.finish() },
					enabled = submitable.value
				) {
					Text("Amend")
				}
			}
		}
	}
}

