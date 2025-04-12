package org.british_information_technologies.gym_library.data_type

/**
 * Mapping of different equipment classes
 */
sealed class EquipmentClass {
	data object Machine :
		EquipmentClass()                      // A type that is a machine

	data object UsesUserWeight :
		EquipmentClass()                      // A type of machine that uses the users weight (weights should be subtracted)

	data object FreeWeight :
		EquipmentClass()                      // A type that uses free weights, or plates.

	data object None :
		EquipmentClass()                      // A type that doesn't use any equipment, except ones own weight

	data class Undefined(val text: String) :
		EquipmentClass()                      // A type not yet defined, used for if i make a mistake :D
}