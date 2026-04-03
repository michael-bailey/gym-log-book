package net.michael_bailey.gym_log_book.shared.exercise.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class EquipmentClass {
	@Serializable
	@SerialName("Machine")
	data object Machine : EquipmentClass()

	@Serializable
	@SerialName("UsesUserWeight")
	data object UsesUserWeight : EquipmentClass()

	@Serializable
	@SerialName("FreeWeight")
	data object FreeWeight : EquipmentClass()

	@Serializable
	@SerialName("None")
	data object None : EquipmentClass()

	@Serializable
	@SerialName("Undefined")
	data class Undefined(val text: String) : EquipmentClass()
}
