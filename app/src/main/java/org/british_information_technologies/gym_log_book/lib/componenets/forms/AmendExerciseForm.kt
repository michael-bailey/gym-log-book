package org.british_information_technologies.gym_log_book.lib.componenets.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.british_information_technologies.gym_log_book.activity.amend_exercise_activity_v2.components.ExerciseTypeDropdownSelector
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseEntry
import org.british_information_technologies.gym_log_book.lib.componenets.ValidatorTextField
import org.british_information_technologies.gym_log_book.lib.validation.Validator
import java.util.UUID

@Composable
fun AmendExerciseEntryForm(
	modifier: Modifier = Modifier,
	exercise: EntExerciseEntry,
	exercises: Map<UUID, String>,
	onSubmit: (EntExerciseEntry) -> Unit,
	onCancel: () -> Unit
) {

	var selectedType by remember { mutableStateOf(exercise.exerciseTypeId) }
	var setNumber by remember { mutableStateOf(exercise.setNumber.toString()) }
	var weight by remember { mutableStateOf(exercise.weight.toString()) }
	var reps by remember { mutableStateOf(exercise.reps.toString()) }

	val isValid by remember {
		derivedStateOf {
			val setNumberIsValid =
				Validator.FloatValidator().validator(setNumber).isSuccess
			val weightIsValid = Validator.FloatValidator().validator(weight).isSuccess
			val repsIsValid = Validator.FloatValidator().validator(reps).isSuccess

			setNumberIsValid && weightIsValid && repsIsValid
		}
	}

	Column(
		modifier,
		Arrangement.SpaceEvenly,
		Alignment.CenterHorizontally
	) {
		Column(Modifier.wrapContentSize()) {
			ExerciseTypeDropdownSelector(
				exercises = exercises,
				selectedType = exercises[selectedType],
				setExercise = { selectedType = it }
			)
			ValidatorTextField(
				state = setNumber,
				validator = Validator.NumberValidator(),
				placeholder = "Set Number...",
				onChange = {
					setNumber = it
				}
			)

			ValidatorTextField(
				state = weight,
				validator = Validator.FloatValidator(),
				placeholder = "Weight...",
				onChange = {
					weight = it
				}
			)

			ValidatorTextField(
				state = reps,
				validator = Validator.NumberValidator(true),
				placeholder = "Reps...",
				onChange = {
					reps = it
				}
			)
		}
		Column {
			Row(
				Modifier.fillMaxWidth(),
				Arrangement.SpaceEvenly,
				Alignment.CenterVertically
			) {
				Button(onClick = { onCancel() }) {
					Text("Cancel")
				}
				Button(
					enabled = isValid,
					onClick = {
						onSubmit(
							exercise.copy(
								exerciseTypeId = selectedType,
								setNumber = setNumber.toInt(),
								weight = weight.toDouble(),
								reps = reps.toInt(),
							)
						)
					}
				) {
					Text("Submit")
				}
			}
		}
	}
}