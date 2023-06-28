package io.github.michael_bailey.gym_log_book.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseEntry
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 */
interface ExerciseEntryDao {

	@Query(
		"""
			SELECT * FROM exercise_items
		"""
	)
	fun queryAllExercise(): LiveData<List<EntExerciseEntry>>

	@Query(
		"""
			SELECT * FROM exercise_items
		"""
	)
	fun flowAllExercise(): Flow<List<EntExerciseEntry>>

	@Query(
		"""
			SELECT * FROM exercise_items
		"""
	)
	fun getAllExercise(): List<EntExerciseEntry>


	@Query(
		"""
			SELECT * FROM exercise_items
			WHERE id == :id
		"""
	)
	suspend fun queryExercise(id: UUID): EntExerciseEntry

	@Query(
		"""
			SELECT * FROM exercise_items
			WHERE exerciseTypeId == :exercise
		"""
	)
	suspend fun getExercisesByType(exercise: UUID): List<EntExerciseEntry>

	@Query(
		"""
			SELECT COUNT(id)
			FROM exercise_items
		"""
	)
	fun exerciseCount(): Flow<Int>

	@Insert
	suspend fun insertExercise(exercise: EntExerciseEntry)

	@Update
	fun updateExercise(exercise: EntExerciseEntry)

	@Update
	suspend fun updateExercises(updatedExercises: List<EntExerciseEntry>)

	@Delete
	fun deleteExercise(exercise: EntExerciseEntry)


}