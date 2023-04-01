package io.github.michael_bailey.gym_log_book.lib.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ListViewModel(
	private val _revealedCardsList: MutableStateFlow<List<Int>> =
		MutableStateFlow(listOf()),
) : ViewModel() {

	val revealedCardsList: MutableStateFlow<List<Int>> get() = _revealedCardsList

	fun onItemExpanded(cardId: Int) {
		if (_revealedCardsList.value.contains(cardId)) return
		_revealedCardsList.value =
			_revealedCardsList.value.toMutableList().also { list ->
				list.add(cardId)
			}
	}

	fun onItemCollapsed(cardId: Int) {
		if (!_revealedCardsList.value.contains(cardId)) return
		_revealedCardsList.value =
			_revealedCardsList.value.toMutableList().also { list ->
				list.remove(cardId)
			}
	}
}