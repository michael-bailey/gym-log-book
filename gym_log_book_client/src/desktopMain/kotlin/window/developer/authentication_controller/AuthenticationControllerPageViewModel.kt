package net.michael_bailey.gym_log_book.client.window.developer.authentication_controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

class AuthenticationControllerPageViewModel(
	private val authenticationService: AuthenticationService,
) : ViewModel() {

	private val _currentToken = MutableStateFlow<AuthenticationTokenPair?>(null)
	private val _currentExceptionMessage = MutableStateFlow<String?>(null)

	val isAuthenticated = authenticationService.isAuthenticated
	val currentExceptionMessage: Flow<String?> = _currentExceptionMessage.onEach {
		println("new error $it")
	}

	fun authenticate(
		accessToken: String,
		refreshToken: String,
	) {
		viewModelScope.launch {

			val token = AuthenticationTokenPair(
				accessToken = accessToken,
				refreshToken = refreshToken
			)

			authenticationService.authenticate(token)
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
}