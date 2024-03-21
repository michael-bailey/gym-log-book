package org.british_information_technologies.gym_log_book.activity.add_exercise_type_activity

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

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
			Text(modifier = Modifier.padding(it), text = "")
		}
	)
}