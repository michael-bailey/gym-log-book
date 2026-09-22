package net.michael_bailey.gym_log_book.client.authentication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationLoginService

class LoginPageViewModel(
	private val authenticationLoginService: AuthenticationLoginService
) : ViewModel() {

	private val usernameFlow = MutableStateFlow("")
	private val passwordFlow = MutableStateFlow("")

	fun onUsernameChanged(text: CharSequence) = viewModelScope.launch {
		usernameFlow.emit(text.toString())
	}

	fun onPasswordChanged(text: CharSequence) = viewModelScope.launch {
		passwordFlow.emit(text.toString())
	}

	fun submit() = viewModelScope.launch {
		authenticationLoginService.login(
			username = usernameFlow.value,
			password = passwordFlow.value
		)
	}

	fun cancel() = viewModelScope.launch {

	}

}