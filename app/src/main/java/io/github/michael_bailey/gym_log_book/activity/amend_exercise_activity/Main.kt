package io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

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
			OutlinedTextField(
				value = name.value,
				onValueChange = vm::setName,
				singleLine = true,
				isError = nameError.value != "",
				label = { Text("Name...") }
			)
			Text(nameError.value, color = Color.Red)

			OutlinedTextField(
				value = set.value,
				onValueChange = vm::setSet,
				label = { Text("Set Number...") },
				singleLine = true,
				isError = setError.value != "",
				keyboardOptions = numberKeyboard
			)
			Text(setError.value, color = Color.Red)

			OutlinedTextField(
				value = weight.value,
				onValueChange = vm::setWeight,
				placeholder = { Text("Weight...") },
				singleLine = true,
				isError = weightError.value != "",
				keyboardOptions = numberKeyboard
			)
			Text(weightError.value, color = Color.Red)

			OutlinedTextField(
				value = reps.value,
				onValueChange = vm::setReps,
				placeholder = { Text("Reps...") },
				singleLine = true,
				isError = repsError.value != "",
				keyboardOptions = numberKeyboard
			)
			Text(repsError.value, color = Color.Red)

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

