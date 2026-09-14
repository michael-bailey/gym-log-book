package net.michael_bailey.gym_log_book.client

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService

class ApplicationViewModel(
	private val authenticationService: AuthenticationService
) : ViewModel() {

	val isLoginWindowShown = mutableStateOf(true)

	init {
		authenticationService.isAuthenticated
			.onEach(::onIsAuthenticatedUpdated).launchIn(viewModelScope)
	}

	private fun onIsAuthenticatedUpdated(isAuthenticated: Boolean) {
		isLoginWindowShown.value = !isAuthenticated
	}
}