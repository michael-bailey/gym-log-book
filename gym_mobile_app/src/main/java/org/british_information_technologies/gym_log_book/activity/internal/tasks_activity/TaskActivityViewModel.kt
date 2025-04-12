package org.british_information_technologies.gym_log_book.activity.internal.tasks_activity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.repository.TaskRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskActivityViewModel @Inject constructor(
	private val taskRepository: TaskRepository,
) : ViewModel() {

	private val _currentTaskString: MutableState<String> = mutableStateOf("")

	val currentTaskString: State<String> = _currentTaskString
	val tasks = taskRepository.tasks.asLiveData()


	fun setTaskString(text: String) {
		_currentTaskString.value = text
	}

	fun addTask() = viewModelScope.launch(Dispatchers.IO) {
		taskRepository.addTask(
			task = currentTaskString.value,
		)
		_currentTaskString.value = ""
	}

	fun deleteTask(id: UUID) = viewModelScope.launch(Dispatchers.IO) {
		taskRepository.deleteTask(id)
	}

}