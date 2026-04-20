@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.shared.exercise.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class ExerciseType(
	override val id: Uuid,
	val name: String,
	val equipmentClass: EquipmentClass,
	val isUsingUserWeight: Boolean,
) : Identifiable()