package net.michael_bailey.gym_log_book.client.authentication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationLoginService
import net.michael_bailey.gym_log_book.client.authentication.state.LoginViewState

class LoginPageViewModel(
	private val authenticationLoginService: AuthenticationLoginService
) : ViewModel() {
	val loginFormState = LoginViewState()

	fun submit() = viewModelScope.launch {
		authenticationLoginService.login(
			username = loginFormState.usernameFieldState.text.toString(),
			password = loginFormState.passwordFieldState.text.toString()
		)
	}

	fun cancel() = viewModelScope.launch {

	}

}