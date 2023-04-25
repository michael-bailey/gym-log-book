package io.github.michael_bailey.gym_log_book.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.michael_bailey.gym_log_book.database.entity.EntWeightEntry
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 */
interface WeightItemDao {

	@Query(
		"""
			SELECT * FROM weight_items
		"""
	)
	fun queryAllWeight(): LiveData<List<EntWeightEntry>>

	@Query(
		"""
			SELECT * FROM weight_items
			WHERE id == :id
		"""
	)
	fun queryExercise(id: UUID): LiveData<EntWeightEntry>

	@Insert
	fun insertExercise(exercise: EntWeightEntry)

	@Update
	fun updateExercise(exercise: EntWeightEntry)

	@Delete
	fun deleteExercise(exercise: EntWeightEntry)
}