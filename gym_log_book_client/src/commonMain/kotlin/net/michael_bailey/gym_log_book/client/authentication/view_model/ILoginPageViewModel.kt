package net.michael_bailey.gym_log_book.client.authentication.view_model

import kotlinx.coroutines.flow.MutableStateFlow

interface ILoginPageViewModel {
	val usernameFlow: MutableStateFlow<String>
	val passwordFlow: MutableStateFlow<String>
	fun onUsernameChanged(text: CharSequence)
	fun onPasswordChanged(text: CharSequence)
	fun submit()
	fun cancel()
}