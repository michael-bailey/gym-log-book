package io.github.michael_bailey.gym_log_book.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.michael_bailey.gym_log_book.database.entity.EntWeightEntry
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 */
interface WeightEntryDao {

	@Query(
		"""
			SELECT * FROM weight_items
		"""
	)
	fun queryAllWeight(): Flow<List<EntWeightEntry>>

	@Query(
		"""
			SELECT * FROM weight_items
			WHERE id == :id
		"""
	)
	fun queryWeight(id: UUID): LiveData<EntWeightEntry>

	@Query(
		"""
			SELECT * FROM weight_items
			WHERE id == :id
		"""
	)
	suspend fun getWeight(id: UUID): EntWeightEntry

	@Insert
	suspend fun insertWeight(weight: EntWeightEntry)

	@Update
	fun updateWeight(weight: EntWeightEntry)

	@Delete
	fun deleteWeight(weight: EntWeightEntry)
	suspend fun create(weight: Double) {
		this.insertWeight(
			EntWeightEntry(
				value = weight
			)
		)
	}


}