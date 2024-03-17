package org.british_information_technologies.gym_log_book.activity.main_activity.dialogue

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivityViewModel
import org.british_information_technologies.gym_log_book.lib.componenets.CheckBox
import org.british_information_technologies.gym_log_book.lib.componenets.ValidatorTextField
import org.british_information_technologies.gym_log_book.lib.validation.Validator
import org.british_information_technologies.gym_log_book.theme.Title

@Composable
fun ExerciseTypeCreateDialogue(
	vm: MainActivityViewModel,
	onDismiss: () -> Unit
) {

	val activity = runCatching { LocalContext.current as Activity }.getOrNull()

	var exerciseName by remember { mutableStateOf("") }
	val isUsingUserWeight = remember { mutableStateOf(false) }
	val canSubmit by remember {
		derivedStateOf {
			exerciseName.isNotEmpty()
		}
	}

	Dialog(onDismissRequest = onDismiss) {
		Surface(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight(),
			shape = RoundedCornerShape(28.dp),
			content = {
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
							onChange = { exerciseName = it }
						)
						CheckBox(isUsingUserWeight, "Uses your Weight?") {
							isUsingUserWeight.value = it
						}
					}
					Row(
						Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.SpaceEvenly,
						verticalAlignment = Alignment.CenterVertically
					) {
						Button(onClick = onDismiss) {
							Text(text = "Cancel")
						}
						Button(
							enabled = canSubmit,
							onClick = {
								vm.createNewType(
									exerciseName,
									isUsingUserWeight.value
								); onDismiss()
							}
						) {
							Text(text = "Submit")
						}
					}
				}
			}
		)
	}
}