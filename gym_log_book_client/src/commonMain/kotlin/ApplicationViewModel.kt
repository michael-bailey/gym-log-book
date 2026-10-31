package net.michael_bailey.gym_log_book.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService

class ApplicationViewModel(
	private val authenticationService: AuthenticationService
) : ViewModel() {
	val isLoginWindowShown = authenticationService.isAuthenticated
		.map { !it }
		.stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = true)
}