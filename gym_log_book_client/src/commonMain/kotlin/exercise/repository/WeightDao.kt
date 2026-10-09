package net.michael_bailey.gym_log_book.client.exercise.repository

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Update
import net.michael_bailey.gym_log_book.client.exercise.entity.WeightEntryEntity

@Dao
interface WeightDao {

	@Insert
	suspend fun createWeightEntry(entity: WeightEntryEntity)

	@Delete
	suspend fun deleteWeightEntry(entity: WeightEntryEntity)

	@Update
	suspend fun updateWeightEntry(entity: WeightEntryEntity)

}