package io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity_v2

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.github.michael_bailey.gym_log_book.activity.add_exercise_activity.AmendExerciseViewModelV2
import io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity_v2.components.ExerciseTypeDropdownSelector
import io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity.DebugSettingsActivity
import io.github.michael_bailey.gym_log_book.lib.Validator
import io.github.michael_bailey.gym_log_book.lib.componenets.ValidatorTextField

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Main(vm: AmendExerciseViewModelV2) {

	val activity = LocalContext.current as Activity

	val exercises by vm.exerciseNameMap.observeAsState(initial = mapOf())

	val selectedType by vm.selectedExerciseType.observeAsState("")
	val setNumber by vm.setNumber.observeAsState("")
	val weight by vm.weight.observeAsState("")
	val reps by vm.reps.observeAsState("")

	val scrollState =
		TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

	Scaffold(
		topBar = {
			TopAppBar(
				scrollBehavior = scrollState,
				title = { Text("Edit Exercise") },
				actions = {
					IconButton(
						onClick = {
							activity.startActivity(
								Intent(
									activity.applicationContext,
									DebugSettingsActivity::class.java
								)
							)
						},
						content = { Icon(Icons.Default.MoreVert, "content") }
					)
				}
			)
		},
		content = {
			Column(
				Modifier
					.padding(it)
					.fillMaxSize(),
				Arrangement.SpaceEvenly,
				Alignment.CenterHorizontally
			) {
				Column(Modifier.wrapContentSize()) {
					ExerciseTypeDropdownSelector(
						exercises = exercises,
						selectedType = selectedType,
						setExercise = { vm.setExercise(it) }
					)
					ValidatorTextField(
						state = setNumber,
						validator = Validator.NumberValidator,
						placeholder = "Set Number...",
						onChange = {
							vm.setSetNumber(it)
						}
					)

					ValidatorTextField(
						state = weight,
						validator = Validator.FloatValidator,
						placeholder = "Weight...",
						onChange = {
							vm.setWeight(it)
						}
					)

					ValidatorTextField(
						state = reps,
						validator = Validator.NumberValidator,
						placeholder = "Reps...",
						onChange = {
							vm.setReps(it)
						}
					)
				}
				Column {
					Row(
						Modifier.fillMaxWidth(),
						Arrangement.SpaceEvenly,
						Alignment.CenterVertically
					) {
						Button(onClick = { activity.finish() }) {
							Text("Cancel")
						}
						Button(onClick = { vm.finalise(activity) }) {
							Text("Submit")
						}
					}
				}
			}
		}
	)
}