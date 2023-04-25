package io.github.michael_bailey.gym_log_book.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseType
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 */
interface ExerciseTypeDao {

	@Query(
		"""
			SELECT * FROM exercise_types
		"""
	)
	fun queryAllExerciseTypes(): LiveData<List<EntExerciseType>>

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE id == :id
		"""
	)
	fun queryExercise(id: UUID): LiveData<EntExerciseType>

	@Insert
	fun insertExercise(exercise: EntExerciseType)

	@Update
	fun updateExercise(exercise: EntExerciseType)

	@Delete
	fun deleteExercise(exercise: EntExerciseType)
}