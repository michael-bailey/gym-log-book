package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass

@Composable
fun EquipmentClassDropDown(
	equipmentClassState: MutableState<EquipmentClass>,
	equipmentClasses: Map<EquipmentClass, String>,
	modifier: Modifier = Modifier,
) {

	val (isDropDownOpen, setIsDropdownOpen) = remember { mutableStateOf(false) }
	var state by equipmentClassState

	Box(
		modifier = modifier
	) {
		OutlinedButton(
			onClick = { setIsDropdownOpen(true) }) {
			Text("Equipment: ${equipmentClasses[state]}")
		}
		DropdownMenu(
			expanded = isDropDownOpen,
			onDismissRequest = { setIsDropdownOpen(false) },
		) {
			equipmentClasses.forEach { (equipmentClass, displayName) ->
				DropdownMenuItem(text = { Text(displayName) }, onClick = {
					state = equipmentClass
					setIsDropdownOpen(false)
				})
			}
		}
	}
}

@Preview
@Composable
fun EquipmentClassDropDown_Preview() {

	val equipmentClassState = mutableStateOf<EquipmentClass>(EquipmentClass.None)

	val classes = listOf(
		EquipmentClass.None,
		EquipmentClass.FreeWeight,
		EquipmentClass.Machine,
		EquipmentClass.UserWeightMachine,
		EquipmentClass.Calisthenics,
		EquipmentClass.Undefined("Test Undefined")
	).associateWith { it.displayName }

	EquipmentClassDropDown(
		equipmentClassState = equipmentClassState,
		equipmentClasses = classes,
	)
}

fun displayName(equipmentClass: EquipmentClass): String = when (equipmentClass) {
	EquipmentClass.FreeWeight -> "Free Weight"
	EquipmentClass.Machine -> "Machine"
	EquipmentClass.None -> "Please Select"
	EquipmentClass.UserWeightMachine -> "User Weight Machine"
	EquipmentClass.Calisthenics -> "Calisthenics"
	is EquipmentClass.Undefined -> "undefined name:${equipmentClass.text}"
}

val EquipmentClass.displayName: String
	get() = displayName(this)