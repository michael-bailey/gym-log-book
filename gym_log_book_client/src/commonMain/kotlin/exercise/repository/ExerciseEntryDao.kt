@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import androidx.room3.*
import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Dao
interface ExerciseEntryDao {

	@Query(
		"""
		SELECT id FROM exercise_items
		"""
	)
	fun allExerciseEntryIdsFlow(): Flow<List<Uuid>>

	@Query(
		"""
		SELECT * FROM exercise_items
		WHERE id = :id
		"""
	)
	fun exerciseEntryFlow(id: Uuid): Flow<ExerciseEntryEntity>

	@Query(
		"""
			SELECT * FROM exercise_items
			WHERE id = :id
			LIMIT 1
		"""
	)
	suspend fun exerciseEntry(id: Uuid): ExerciseEntryEntity

	@Insert
	suspend fun createExerciseEntry(entity: ExerciseEntryEntity)

	@Update
	suspend fun updateExerciseEntry(entity: ExerciseEntryEntity)

	@Delete
	suspend fun deleteExerciseEntry(entity: ExerciseEntryEntity)
}