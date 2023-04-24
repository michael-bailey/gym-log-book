package io.github.michael_bailey.gym_log_book.activity.tasks_activity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.github.michael_bailey.gym_log_book.database.dao.TaskDao
import io.github.michael_bailey.gym_log_book.database.entity.EntTask

class TaskActivityViewModel(
	private val taskDao: TaskDao,
	private val _currentTaskString: MutableState<String> = mutableStateOf("")
) : ViewModel() {

	val currentTaskString: State<String> = _currentTaskString
	val tasks = taskDao.queryTasks()

	fun setTaskString(text: String) {
		_currentTaskString.value = text
	}

	fun addTask() {
		taskDao.insertTask(
			EntTask(
				text = currentTaskString.value,
				isComplete = false
			)
		)
		_currentTaskString.value = ""
	}

	fun deleteTask(task: EntTask) {
		taskDao.deleteTask(task)
	}

}