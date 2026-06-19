@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.mapper

import net.michael_bailey.gym_log_book.server.database.schema.v1.ExerciseTypeTable
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import org.jetbrains.exposed.v1.core.ResultRow
import kotlin.uuid.ExperimentalUuidApi

fun ResultRow.toExerciseTypeDomain(): ExerciseTypeModel = ExerciseTypeModel(
	id = this[ExerciseTypeTable.id].value,
	name = this[ExerciseTypeTable.name],
	equipmentClass = EquipmentClass.fromString(
		classType = this[ExerciseTypeTable.equipmentClass],
		text = this[ExerciseTypeTable.equipmentClassText],
	)
)