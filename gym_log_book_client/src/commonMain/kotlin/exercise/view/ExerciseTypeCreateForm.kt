package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.michael_bailey.gym_log_book.client.exercise.state.ExerciseTypeCreateFormState

@Composable
fun ExerciseTypeCreateForm(
	modifier: Modifier = Modifier,
	formState: ExerciseTypeCreateFormState = ExerciseTypeCreateFormState(),
	onSubmit: () -> Unit = { },
	onDismiss: () -> Unit = { },
) {

	val typeNameState = formState.typeNameFieldState
	val equipmentClassState = formState.typeClassFieldState
	val equipmentClassMap = formState.exerciseClassOptionsMap

	val canSubmit by formState.canSubmit

	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		Text("Create Type", fontSize = 24.sp)
		OutlinedTextField(state = typeNameState)
		EquipmentClassDropDown(
			equipmentClassState = equipmentClassState,
			equipmentClasses = equipmentClassMap,
		)
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			OutlinedButton(
				onClick = onDismiss,
			) {
				Text("Cancel")
			}
			Button(
				enabled = canSubmit,
				onClick = onSubmit,
			) {
				Text("Submit")
			}
		}
	}
}

@Preview
@Composable
fun ExerciseTypeCreateForm_Preview() {
	ExerciseTypeCreateForm(
		modifier = Modifier.width(IntrinsicSize.Min)
	)
}