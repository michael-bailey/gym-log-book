package net.michael_bailey.gym_log_book.client.window.developer.login_controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.shared.authentication.controller.LoginTokenController
import net.michael_bailey.gym_log_book.shared.authentication.exception.LoginTokenResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

class LoginControllerPageViewModel(
	private val loginController: LoginTokenController
) : ViewModel() {

	private val _currentToken = MutableStateFlow<AuthenticationTokenPair?>(null)
	val currentToken: Flow<AuthenticationTokenPair?> = _currentToken

	private val _currentExceptionMessage = MutableStateFlow<String?>(null)
	val currentExceptionMessage: Flow<String?> = _currentExceptionMessage

	val hasToken = _currentToken.map { it != null }.onEach {
		println("hasToken: $it")
	}
	val accessToken = _currentToken.map { it?.accessToken ?: "None" }
	val refreshToken = _currentToken.map { it?.refreshToken ?: "None" }

	fun login(username: String, password: String) = this.viewModelScope.launch {

		resetOutputs()

		when (
			val result = loginController
				.getAuthenticationTokenPair(username, password)
		) {
			is LoginTokenResult.Ok -> handleSuccess(result.value)
			is LoginTokenResult.Err -> handleResult(result.reason)
		}
	}

	fun reset() {
		viewModelScope.launch {
			resetOutputs()
		}
	}

	private suspend fun resetOutputs() {
		_currentToken.emit(null)
		_currentExceptionMessage.emit(null)
	}

	private suspend fun handleSuccess(tokenPair: AuthenticationTokenPair) {
		_currentToken.emit(tokenPair)
	}

	private suspend fun handleResult(reason: LoginTokenResult.Reason) {
		_currentExceptionMessage.emit(reason.message)
	}
}