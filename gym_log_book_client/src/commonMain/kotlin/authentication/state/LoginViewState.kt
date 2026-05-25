package net.michael_bailey.gym_log_book.client.authentication.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf

class LoginViewState {
	val usernameFieldState = TextFieldState("")
	val passwordFieldState = TextFieldState("")
	val isPasswordShownState = mutableStateOf(false)

	val textObfuscationState = derivedStateOf {
		if (isPasswordShownState.value)
			TextObfuscationMode.Visible
		else
			TextObfuscationMode.RevealLastTyped
	}
}