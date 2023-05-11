package io.github.michael_bailey.gym_log_book.database.dao

import androidx.room.*
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseType
import io.github.michael_bailey.gym_log_book.database.importer.ExportDao
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 */
abstract class ExerciseTypeDao : ExportDao<EntExerciseType> {

	@Query(
		"""
			SELECT * FROM exercise_types
		"""
	)
	abstract suspend fun queryAllExerciseTypes(): List<EntExerciseType>

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE id == :id
		"""
	)
	abstract suspend fun queryExercise(id: UUID): EntExerciseType

	@Insert
	abstract suspend fun insertExercise(exercise: EntExerciseType)

	@Update
	abstract suspend fun updateExercise(exercise: EntExerciseType)

	@Delete
	abstract suspend fun deleteExercise(exercise: EntExerciseType)

	@Insert
	// required for export interface
	abstract override fun insert(ent: EntExerciseType)
}