@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseTypeRepository {
	val allExerciseTypes: Flow<Collection<ExerciseType>>
	fun getExerciseType(id: Uuid): Flow<ExerciseType?>
	suspend fun getExerciseTypeAsync(id: Uuid): ExerciseType?
	suspend fun getExerciseTypeByNameAsync(name: String): ExerciseType?
}