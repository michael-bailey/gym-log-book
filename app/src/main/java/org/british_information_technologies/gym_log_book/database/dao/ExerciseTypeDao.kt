package org.british_information_technologies.gym_log_book.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 */
abstract class ExerciseTypeDao {
	@Query(
		"""
			SELECT * FROM exercise_types
		"""
	)
	abstract fun queryAllExerciseTypeFlow(): Flow<List<EntExerciseType>>

	@Query(
		"""
			SELECT id FROM exercise_types
		"""
	)
	abstract fun queryAllExerciseTypeIdFlow(): Flow<List<UUID>>

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE id == :id
		"""
	)
	abstract fun queryExerciseTypeFlow(id: UUID): Flow<EntExerciseType>

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE id == :id
		"""
	)
	abstract suspend fun queryExercise(id: UUID): EntExerciseType?

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE name == :name
		"""
	)
	abstract suspend fun queryByName(name: String): EntExerciseType?

	@Insert
	abstract suspend fun insertExercise(exerciseType: EntExerciseType)

	@Update
	abstract suspend fun updateExercise(exerciseType: EntExerciseType)

	@Delete
	abstract suspend fun deleteExercise(exerciseType: EntExerciseType)

	@Query(
		"""
			SELECT COUNT(id)
			FROM exercise_types
		"""
	)
	abstract fun exerciseTypeCount(): Flow<Int>
}