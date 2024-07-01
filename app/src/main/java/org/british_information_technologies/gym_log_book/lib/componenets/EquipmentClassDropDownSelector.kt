package org.british_information_technologies.gym_log_book.lib.componenets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import org.british_information_technologies.gym_log_book.data_type.EquipmentClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentClassDropDownSelector(
	selectedClass: EquipmentClass?,
	isLast: Boolean = false,
	setEquipmentClass: (EquipmentClass) -> Unit
) {

	val selectableClasses = listOf(
		EquipmentClass.Machine,
		EquipmentClass.UsesUserWeight,
		EquipmentClass.FreeWeight,
		EquipmentClass.None,
	)

	var dropdownExposed by remember { mutableStateOf(false) }
	val error = if (selectedClass == null) {
		"Select equipment class"
	} else {
		""
	}
	Column {
		ExposedDropdownMenuBox(
			expanded = dropdownExposed,
			onExpandedChange = { dropdownExposed = !dropdownExposed }
		) {
			OutlinedTextField(
				modifier = Modifier.menuAnchor(),
				value = selectedClass?.toString() ?: "",
				label = { Text("Equipment Class...") },
				onValueChange = {},
				isError = (selectedClass == null),
				keyboardOptions = KeyboardOptions(
					autoCorrect = false,
					imeAction = if (isLast) {
						ImeAction.Next
					} else {
						ImeAction.Done
					}
				)
			)
			ExposedDropdownMenu(
				expanded = dropdownExposed,
				onDismissRequest = { dropdownExposed = false }) {
				for (i in selectableClasses) {
					DropdownMenuItem(
						text = { Text(i.toString()) },
						onClick = { setEquipmentClass(i); dropdownExposed = false })
				}
			}
		}
		Text(error, color = Color.Red)
	}
}
