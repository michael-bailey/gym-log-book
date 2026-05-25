@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IMutableExerciseTypeRepository : IExerciseTypeRepository {
	suspend fun createType(newExerciseType: NewExerciseTypeModel): ExerciseTypeModel
	suspend fun deleteTypes(ids: Collection<Uuid>)
}
