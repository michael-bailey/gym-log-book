package org.british_information_technologies.gym_log_book.database.converter

import androidx.room.TypeConverter
import org.british_information_technologies.gym_log_book.data_type.EquipmentClass

class EquipmentClassConverter {

	@TypeConverter
	fun equipmentClassToString(equipmentClass: EquipmentClass): String =
		when (equipmentClass) {
			is EquipmentClass.Machine -> "machine"
			is EquipmentClass.UsesUserWeight -> "user_weight_machine"
			is EquipmentClass.FreeWeight -> "free_weight"
			is EquipmentClass.None -> "none"
			is EquipmentClass.Undefined -> equipmentClass.text
		}

	@TypeConverter
	fun stringToEquipmentClass(string: String): EquipmentClass = when (string) {
		"machine" -> EquipmentClass.Machine
		"user_weight_machine" -> EquipmentClass.UsesUserWeight
		"free_weight" -> EquipmentClass.FreeWeight
		"none" -> EquipmentClass.None
		else -> EquipmentClass.Undefined(text = string)
	}
}