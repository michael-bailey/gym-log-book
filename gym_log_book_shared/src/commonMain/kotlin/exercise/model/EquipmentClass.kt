package net.michael_bailey.gym_log_book.shared.exercise.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class EquipmentClass {
	@Serializable
	@SerialName("Machine")
	data object Machine : EquipmentClass()

	@Serializable
	@SerialName("UserWeightMachine")
	data object UserWeightMachine : EquipmentClass()

	@Serializable
	@SerialName("Calisthenics")
	data object Calisthenics : EquipmentClass()

	@Serializable
	@SerialName("FreeWeight")
	data object FreeWeight : EquipmentClass()

	@Serializable
	@SerialName("None")
	data object None : EquipmentClass()

	@Serializable
	@SerialName("Undefined")
	data class Undefined(val text: String) : EquipmentClass()

	override fun toString(): String {
		return this::class.simpleName!!
	}

	companion object {
		fun fromString(classType: String, text: String?): EquipmentClass =
			when (classType) {
				Machine::class.simpleName -> Machine
				UserWeightMachine::class.simpleName -> UserWeightMachine
				Calisthenics::class.simpleName -> Calisthenics
				FreeWeight::class.simpleName -> FreeWeight
				None::class.simpleName -> None
				Undefined::class.simpleName -> Undefined(text ?: "No text")
				else -> Undefined("$classType:${text ?: "No text"}")
			}
	}
}
