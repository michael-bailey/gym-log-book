package org.british_information_technologies.gym_log_book.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseEntry
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 */
interface ExerciseEntryDao {

	@Query(
		"""
			SELECT * FROM exercise_items
			ORDER BY exercise_items.createdDate DESC, exercise_items.createdTime DESC
		"""
	)
	fun queryAllExercise(): LiveData<List<EntExerciseEntry>>

	@Query(
		"""
			SELECT * FROM exercise_items
			ORDER BY exercise_items.createdDate DESC, exercise_items.createdTime DESC
		"""
	)
	fun flowAllExercise(): Flow<List<EntExerciseEntry>>

	@Query(
		"""
			SELECT * FROM exercise_items
			ORDER BY exercise_items.createdDate DESC, exercise_items.createdTime DESC
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
			ORDER BY exercise_items.createdDate DESC, exercise_items.createdTime DESC
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