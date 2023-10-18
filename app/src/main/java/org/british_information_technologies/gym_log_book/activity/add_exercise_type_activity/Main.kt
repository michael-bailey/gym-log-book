package org.british_information_technologies.gym_log_book.activity.add_exercise_type_activity

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import org.british_information_technologies.gym_log_book.lib.componenets.CheckBox
import org.british_information_technologies.gym_log_book.lib.componenets.ValidatorTextField
import org.british_information_technologies.gym_log_book.lib.validation.Validator
import org.british_information_technologies.gym_log_book.theme.Title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: AddExerciseTypeActivityViewModel) {

	val activity = runCatching { LocalContext.current as Activity }.getOrNull()

	val exerciseName by vm.exerciseName.observeAsState("")
	val isUsingUserWeight = vm.isUsingUserWeight.observeAsState(false)
	val canSubmit by vm.canSubmit.observeAsState(false)

	Scaffold(
		topBar = {
			TopAppBar(title = { Text("Add Exercise Type") })
		},
		content = {
			Surface(modifier = Modifier.padding(it)) {
				Column(
					Modifier.fillMaxSize(),
					verticalArrangement = Arrangement.SpaceEvenly,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Text("Hi there", fontSize = Title)
					Column(Modifier.width(IntrinsicSize.Min)) {
						ValidatorTextField(
							state = exerciseName,
							validator = Validator.StringNameValidator(isLast = true),
							placeholder = "Exercise name",
							onChange = { vm.setExerciseName(it) }
						)
						CheckBox(isUsingUserWeight, "Uses your Weight?") {
							vm.setIsUsingUserWeight(it)
						}
					}
					Row(
						Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.SpaceEvenly,
						verticalAlignment = Alignment.CenterVertically
					) {
						Button(onClick = { activity?.finish() }) {
							Text(text = "Cancel")
						}
						Button(
							enabled = canSubmit,
							onClick = { vm.finalise(); activity?.finish() }
						) {
							Text(text = "Submit")
						}
					}
				}
			}
		}
	)
}