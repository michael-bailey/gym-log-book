package org.british_information_technologies.gym_log_book.activity.amend_exercise_activity_v2

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.british_information_technologies.gym_log_book.activity.internal.debug_settings_activity.DebugSettingsActivity
import org.british_information_technologies.gym_log_book.lib.componenets.forms.AmendExerciseEntryForm

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Main(vm: AmendExerciseViewModelV2) {

	val activity = LocalContext.current as Activity

	val exercise by vm.currentExerciseEntry.observeAsState()
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
			if (exercise != null) {
				AmendExerciseEntryForm(
					Modifier.padding(it),
					exercise = exercise!!,
					exercises = exercises,
					onSubmit = {
					},
					onCancel = {

					}
				)
			}
		}
	)
}