package io.github.michael_bailey.gym_log_book.repository

import io.github.michael_bailey.gym_log_book.database.dao.TaskDao
import io.github.michael_bailey.gym_log_book.database.entity.EntTask
import java.util.UUID
import javax.inject.Inject

class TaskRepository @Inject constructor(
	private val taskDao: TaskDao,
) {
	private val currentTasks = taskDao.queryTasks()

	val tasks = currentTasks

	suspend fun addTask(task: String) {
		taskDao.insertTask(
			EntTask(
				text = task,
				isComplete = false
			)
		)
	}

	suspend fun deleteTask(id: UUID) {
		taskDao.deleteTask(taskDao.getTask(id))
	}
}