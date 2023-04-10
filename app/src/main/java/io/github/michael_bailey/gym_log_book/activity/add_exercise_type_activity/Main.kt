package io.github.michael_bailey.gym_log_book.activity.add_exercise_type_activity

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import io.github.michael_bailey.gym_log_book.lib.Validator
import io.github.michael_bailey.gym_log_book.lib.componenets.CheckBox
import io.github.michael_bailey.gym_log_book.lib.componenets.ValidatorTextField
import io.github.michael_bailey.gym_log_book.theme.Title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: AddExerciseTypeActivityViewModel?) {

	val activity = runCatching { LocalContext.current as Activity }.getOrNull()
	val nav = rememberNavController()

	var exerciseName = vm?.exerciseName
		?.observeAsState("") ?: remember {
		mutableStateOf("")
	}
	val isUsingUserWeight = vm?.isUsingUserWeight
		?.observeAsState(false) ?: remember {
		mutableStateOf(false)
	}


	Scaffold(
		topBar = {
			SmallTopAppBar(title = { Text("Add Exercise Type") })
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
							validator = Validator.StringNameValidator,
							placeholder = "Exercise name",
							onChange = { vm?.setExerciseName(it) }
						)
						CheckBox(isUsingUserWeight, "Uses your Weight?") {
							vm?.setIsUsingUserWeight(it)
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
						Button(onClick = { vm?.finalise(); activity?.finish() }) {
							Text(text = "Submit")
						}
					}
				}
			}
		}
	)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	Main(null)
}