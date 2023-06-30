package io.github.michael_bailey.gym_log_book.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.michael_bailey.gym_log_book.database.entity.EntTask
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 */
interface TaskDao {

	@Query(
		"""
			SELECT * FROM tasks
		"""
	)
	fun queryTasks(): Flow<List<EntTask>>

	@Query(
		"""
			SELECT * FROM tasks
			WHERE id == :id
			ORDER BY createdDate, createdTime
		"""
	)
	fun queryTask(id: UUID): LiveData<EntTask>

	@Query(
		"""
			SELECT * FROM tasks
			WHERE id == :id
		"""
	)
	suspend fun getTask(id: UUID): EntTask

	@Insert
	suspend fun insertTask(task: EntTask)

	@Update
	fun updateTask(task: EntTask)

	@Delete
	suspend fun deleteTask(task: EntTask)

}