@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.mapper

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import kotlin.uuid.ExperimentalUuidApi

fun NewExerciseEntryCommand.toDomain(): NewExerciseEntryModel = NewExerciseEntryModel(
	exerciseTypeId = exerciseTypeId,
	creationInstant = creationInstant,
	setNumber = setNumber,
	weight = weight,
	reps = reps,
)

fun ExerciseEntryModel.toShared(
	timeZone: TimeZone = TimeZone.UTC,
): ExerciseEntry = ExerciseEntry(
	id = id,
	date = creationInstant.toLocalDateTime(timeZone),
	exerciseTypeId = exerciseTypeId,
	setNumber = setNumber,
	weight = weight,
	reps = reps,
)

fun ExerciseEntryModel.toView(
	exerciseTypeName: String,
	timeZone: TimeZone = TimeZone.UTC,
): ExerciseEntryView = ExerciseEntryView(
	id = id,
	date = creationInstant.toLocalDateTime(timeZone),
	exerciseTypeId = exerciseTypeId,
	exerciseTypeName = exerciseTypeName,
	setNumber = setNumber,
	weight = weight,
	reps = reps,
)
