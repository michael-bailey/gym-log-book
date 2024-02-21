package org.british_information_technologies.gym_log_book.lib.navigation

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationViewModel(
	override val initialRoute: String
) : INavigationViewModel {

	private val _navigationState = MutableStateFlow(initialRoute)
	override val navigationState = _navigationState.asLiveData()

	override suspend fun navigateTo(route: String) {
		_navigationState.emit(route)
	}
}
