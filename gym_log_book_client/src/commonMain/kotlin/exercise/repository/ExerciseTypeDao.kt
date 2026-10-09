package net.michael_bailey.gym_log_book.client.exercise.repository

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Update
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity

@Dao
interface ExerciseTypeDao {

	@Insert
	suspend fun createExerciseType(entity: ExerciseTypeEntity)

	@Delete
	suspend fun deleteExerciseType(entity: ExerciseTypeEntity)

	@Update
	suspend fun updateExerciseType(entity: ExerciseTypeEntity)

}