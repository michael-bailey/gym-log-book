package org.british_information_technologies.gym_log_book.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.british_information_technologies.gym_log_book.database.entity.EntTask
import java.util.UUID

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