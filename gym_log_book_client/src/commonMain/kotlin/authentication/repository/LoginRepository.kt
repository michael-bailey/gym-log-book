package net.michael_bailey.gym_log_book.client.authentication.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import net.michael_bailey.gym_log_book.shared.authentication.controller.LoginTokenController
import net.michael_bailey.gym_log_book.shared.authentication.exception.LoginTokenResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

class LoginRepository(
	private val controller: Flow<LoginTokenController?>,
	private val coroutineScope: CoroutineScope,
) {

	private val _controllerState = controller.stateIn(coroutineScope, SharingStarted.Eagerly, null)

	fun getAuthenticationTokenPair(
		username: String,
		password: String,
	): Deferred<LoginTokenResult> = coroutineScope.async {
		_controllerState.value?.getAuthenticationTokenPair(username, password) ?: return@async LoginTokenResult.Err(
			LoginTokenResult.Reason.ServiceUnavailable
		)
	}

	fun refreshAuthenticationTokenPair(
		loginTokenPair: AuthenticationTokenPair,
	): Deferred<LoginTokenResult> = coroutineScope.async {
		_controllerState.value?.refreshAuthenticationTokenPair(loginTokenPair) ?: return@async LoginTokenResult.Err(
			LoginTokenResult.Reason.ServiceUnavailable
		)
	}

}