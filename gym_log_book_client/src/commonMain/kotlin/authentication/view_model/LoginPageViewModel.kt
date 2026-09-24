package net.michael_bailey.gym_log_book.client.authentication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationLoginService

class LoginPageViewModel(
	private val authenticationLoginService: AuthenticationLoginService
) : ViewModel(), ILoginPageViewModel {

	override val usernameFlow = MutableStateFlow("")
	override val passwordFlow = MutableStateFlow("")

	override fun onUsernameChanged(text: CharSequence) {
		viewModelScope.launch {
			usernameFlow.emit(text.toString())
		}
	}

	override fun onPasswordChanged(text: CharSequence) {
		viewModelScope.launch {
			passwordFlow.emit(text.toString())
		}
	}

	override fun submit() {
		viewModelScope.launch {
			authenticationLoginService.login(
				username = usernameFlow.value,
				password = passwordFlow.value
			)
		}
	}

	override fun cancel() {
		viewModelScope.launch {

		}
	}
}