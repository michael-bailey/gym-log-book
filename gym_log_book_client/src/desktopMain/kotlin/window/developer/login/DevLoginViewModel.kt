package net.michael_bailey.gym_log_book.client.window.developer.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService

class DevLoginViewModel(
	private val authenticationService: AuthenticationService
) : ViewModel() {

	val isAuthenticated = authenticationService.isAuthenticated

	fun login() = viewModelScope.launch {
		authenticationService.login()
	}

	fun logout() = viewModelScope.launch {
		authenticationService.logout()
	}
}