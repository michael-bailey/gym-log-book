package io.github.michael_bailey.gym_log_book.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseEntry
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
			WHERE id == :id
		"""
	)
	fun queryExercise(id: UUID): LiveData<EntExerciseEntry>

	@Insert
	fun insertExercise(exercise: EntExerciseEntry)

	@Update
	fun updateExercise(exercise: EntExerciseEntry)

	@Delete
	fun deleteExercise(exercise: EntExerciseEntry)
}