package org.british_information_technologies.gym_log_book.activity.main_activity.dialogue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivityViewModel
import org.british_information_technologies.gym_log_book.lib.componenets.CheckBox
import org.british_information_technologies.gym_log_book.lib.componenets.ValidatorTextField
import org.british_information_technologies.gym_log_book.lib.navigation.NavLocal
import org.british_information_technologies.gym_log_book.lib.validation.Validator
import org.british_information_technologies.gym_log_book.theme.Title

@Composable
fun ExerciseTypeCreateDialogue(
	vm: MainActivityViewModel,
) {
	val nav = NavLocal.current
	var exerciseName by remember { mutableStateOf("") }
	val isUsingUserWeight = remember { mutableStateOf(false) }
	val canSubmit by remember {
		derivedStateOf {
			exerciseName.isNotEmpty()
		}
	}
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight(),
		shape = RoundedCornerShape(28.dp),
		content = {
			Column(
				Modifier.padding(24.dp),
				verticalArrangement = Arrangement.spacedBy(8.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text("Add Exercise Type", fontSize = Title)
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
					Button(onClick = { nav!!.popBackStack() }) {
						Text(text = "Cancel")
					}
					Button(
						enabled = canSubmit,
						onClick = {
							vm.createNewType(
								exerciseName,
								isUsingUserWeight.value
							)
							nav!!.popBackStack()
						}
					) {
						Text(text = "Submit")
					}
				}
			}
		}
	)

}