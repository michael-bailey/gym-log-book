package io.github.michael_bailey.gym_log_book.activity.tasks_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.michael_bailey.gym_log_book.database.dao.TaskDao

class TaskActivityViewModelFactory(
	val dao: TaskDao
) : ViewModelProvider.NewInstanceFactory() {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return TaskActivityViewModel(dao) as T
	}
}