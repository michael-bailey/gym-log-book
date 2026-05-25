package net.michael_bailey.gym_log_book.client.exercise.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass

class ExerciseTypeCreateFormState(
	initialName: String = "Type name"
) {

	val typeNameFieldState = TextFieldState(initialText = initialName)
	val typeClassFieldState = mutableStateOf<EquipmentClass>(EquipmentClass.None)

	private val exerciseClassOptions: List<EquipmentClass> = listOf(
		EquipmentClass.Machine,
		EquipmentClass.FreeWeight,
		EquipmentClass.UserWeightMachine,
		EquipmentClass.Calisthenics,
	)

	val exerciseClassOptionsMap = exerciseClassOptions.associateWith { getEquipmentClassString(it) }

	val canSubmit = derivedStateOf {
		typeClassFieldState.value != EquipmentClass.None
	}

	val selectedTypeClassString = derivedStateOf {
		getEquipmentClassString(typeClassFieldState.value)
	}

	private fun getEquipmentClassString(
		equipmentClass: EquipmentClass
	) = when (equipmentClass) {
		EquipmentClass.FreeWeight -> "Free Weight"
		EquipmentClass.Machine -> "Machine"
		EquipmentClass.None -> "Please Select"
		EquipmentClass.UserWeightMachine -> "User Weight Machine"
		EquipmentClass.Calisthenics -> "Calisthenics"
		is EquipmentClass.Undefined -> "undefined name:${equipmentClass.text}"
	}

	fun reset() {
		typeNameFieldState.setTextAndPlaceCursorAtEnd("")
		typeClassFieldState.value = EquipmentClass.None
	}
}
