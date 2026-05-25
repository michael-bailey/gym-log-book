@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseTypeRepository {
	val allExerciseTypes: Flow<Collection<ExerciseTypeModel>>
	fun observeExerciseType(id: Uuid): Flow<ExerciseTypeModel?>
	suspend fun getExerciseType(id: Uuid): ExerciseTypeModel?
	suspend fun getExerciseTypeByName(name: String): ExerciseTypeModel?
}
