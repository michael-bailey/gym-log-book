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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivityViewModel
import org.british_information_technologies.gym_log_book.data_type.EquipmentClass
import org.british_information_technologies.gym_log_book.lib.componenets.EquipmentClassDropDownSelector
import org.british_information_technologies.gym_log_book.lib.componenets.text_fields.ValidatorTextField
import org.british_information_technologies.gym_log_book.lib.navigation.NavLocal
import org.british_information_technologies.gym_log_book.lib.validation.Validator
import org.british_information_technologies.gym_log_book.theme.Title
import java.util.UUID

@Composable
fun ExerciseTypeModifyDialogue(
	vm: MainActivityViewModel,
	id: UUID,
) {

	val nav = NavLocal.current
	val item by vm.getTypeFlow(id).collectAsState(null)

	var exerciseName by remember { mutableStateOf("") }

	var equipmentClass by remember {
		mutableStateOf<EquipmentClass?>(null)
	}

	val canSubmit by remember {
		derivedStateOf {
			exerciseName.isNotEmpty() && equipmentClass != null
		}
	}

	LaunchedEffect(key1 = item) {
		if (item == null) {
			return@LaunchedEffect
		}
		exerciseName = item!!.name
		equipmentClass = item!!.equipmentClass
	}

	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight(),
		shape = RoundedCornerShape(28.dp),
	) {
		Column(
			modifier = Modifier
				.width(IntrinsicSize.Min)
				.padding(24.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text("Modify Type", fontSize = Title)
			ValidatorTextField(
				state = exerciseName,
				validator = Validator.StringNameValidator(isLast = true),
				placeholder = "Exercise name",
				onChange = { exerciseName = it }
			)
			EquipmentClassDropDownSelector(
				selectedClass = equipmentClass,
				isLast = true,
			) { equipmentClass = it }
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
						vm.modifyExerciseType(
							item!!.copy(
								name = exerciseName,
								usesUserWeight = equipmentClass!! is EquipmentClass.UsesUserWeight,
								equipmentClass = equipmentClass!!

							)
						)
						nav!!.popBackStack()
					}
				) {
					Text(text = "Submit")
				}
			}
		}
	}
}