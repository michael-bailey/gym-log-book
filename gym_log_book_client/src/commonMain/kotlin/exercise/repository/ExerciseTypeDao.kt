@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import androidx.room3.*
import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Dao
interface ExerciseTypeDao {

	@Query(
		"""
			SELECT * FROM exercise_types
		"""
	)
	fun allExerciseTypes(): Flow<List<ExerciseTypeEntity>>

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE id = :id
			LIMIT 1
		"""
	)
	suspend fun getExerciseType(id: Uuid): ExerciseTypeEntity

	@Insert
	suspend fun createExerciseType(entity: ExerciseTypeEntity)

	@Delete
	suspend fun deleteExerciseType(entity: ExerciseTypeEntity)

	@Update
	suspend fun updateExerciseType(entity: ExerciseTypeEntity)

}