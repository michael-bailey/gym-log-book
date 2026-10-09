@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Update
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import kotlin.uuid.ExperimentalUuidApi

@Dao
interface ExerciseEntryDao {

	@Insert
	suspend fun createExerciseEntry(entity: ExerciseEntryEntity)

	@Delete
	suspend fun deleteExerciseEntry(entity: ExerciseEntryEntity)

	@Update
	suspend fun updateExerciseEntry(entity: ExerciseEntryEntity)

}