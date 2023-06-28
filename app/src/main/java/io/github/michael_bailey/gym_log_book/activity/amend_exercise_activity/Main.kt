package io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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

	val exerciseId by vm.id.observeAsState("")
	val name by vm.exerciseName.observeAsState("")
	val set by vm.set.observeAsState("")
	val weight by vm.weight.observeAsState("")
	val reps by vm.reps.observeAsState("")

	val nameError by vm.nameError.observeAsState("")
	val setError by vm.setError.observeAsState("")
	val weightError by vm.weightError.observeAsState("")
	val repsError by vm.repsError.observeAsState("")

	val submitable by remember {
		derivedStateOf {
			nameError == "" &&
				setError == "" &&
				weightError == "" &&
				repsError == ""
		}
	}

	Column(
		Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(text = "Amend Exercise", fontSize = 32.sp)
			Text(text = "ID $exerciseId", fontSize = 24.sp)
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
					enabled = submitable
				) {
					Text("Amend")
				}
			}
		}
	}
}

