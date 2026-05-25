@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.domain

import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ExerciseTypeModel(
	val id: Uuid,
	val name: String,
	val equipmentClass: EquipmentClass,
)
