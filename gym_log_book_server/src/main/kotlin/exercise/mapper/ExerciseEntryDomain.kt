@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.mapper

import net.michael_bailey.gym_log_book.server.database.schema.v1.ExerciseEntryTable
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import org.jetbrains.exposed.v1.core.ResultRow
import kotlin.uuid.ExperimentalUuidApi

fun ResultRow.toExerciseEntryDomain(): ExerciseEntryModel = ExerciseEntryModel(
	id = this[ExerciseEntryTable.id].value,
	exerciseTypeId = this[ExerciseEntryTable.exerciseTypeId],
	creationInstant = this[ExerciseEntryTable.createTime],
	setNumber = this[ExerciseEntryTable.setNumber],
	weight = this[ExerciseEntryTable.weight],
	reps = this[ExerciseEntryTable.reps],
)