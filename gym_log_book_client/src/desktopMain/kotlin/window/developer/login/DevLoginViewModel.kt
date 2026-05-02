package net.michael_bailey.gym_log_book.client.window.developer.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationLoginService
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService

class DevLoginViewModel(
	private val authenticationService: AuthenticationService,
	private val loginService: AuthenticationLoginService,
) : ViewModel() {

	val username: TextFieldState = TextFieldState("")
	val password: TextFieldState = TextFieldState("")

	val isAuthenticated = authenticationService.isAuthenticated

	fun login() = viewModelScope.launch {
		loginService.login(username.text.toString(), password.text.toString())
	}

	fun logout() = viewModelScope.launch {
		loginService.logout()
	}
}