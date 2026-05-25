@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.shared.exercise.command

import kotlinx.serialization.Serializable
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi

@Serializable
data class NewExerciseTypeCommand(
	val name: String,
	val equipmentClass: EquipmentClass,
) {
	init {
		require(name.isNotBlank()) { "name must not be blank." }
	}
}
