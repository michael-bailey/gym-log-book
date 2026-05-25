@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.mapper

import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseTypeCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi

fun NewExerciseTypeCommand.toDomain(): NewExerciseTypeModel = NewExerciseTypeModel(
	name = name.trim(),
	equipmentClass = equipmentClass,
)

fun ExerciseTypeModel.toShared(): ExerciseType = ExerciseType(
	id = id,
	name = name,
	equipmentClass = equipmentClass,
)
