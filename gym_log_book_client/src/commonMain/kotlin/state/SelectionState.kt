package net.michael_bailey.gym_log_book.client.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class SelectionState<ID> {
	private val _selectedList = mutableStateOf<Set<ID>>(emptySet())

	val selectedList: State<Set<ID>>
		get() = _selectedList

	fun add(id: ID) {
		_selectedList.value += setOf(id)
	}

	fun remove(id: ID) {
		_selectedList.value -= setOf(id)
	}
}