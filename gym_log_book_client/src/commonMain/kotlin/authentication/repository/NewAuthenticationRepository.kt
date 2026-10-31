@file:OptIn(ExperimentalCoroutinesApi::class)

package net.michael_bailey.gym_log_book.client.authentication.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import net.michael_bailey.gym_log_book.shared.authentication.controller.NewAuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.exception.AuthenticationResult
import net.michael_bailey.gym_log_book.shared.authentication.model.AuthenticationTokenPair

class NewAuthenticationRepository(
	private val controller: Flow<NewAuthenticationController?>,
	private val coroutineScope: CoroutineScope,
) {

	private val _controllerState = controller.stateIn(coroutineScope, SharingStarted.Eagerly, null)

	val isAuthenticated: Flow<Boolean> = controller
		.flatMapLatest { it?.isAuthenticated() ?: emptyFlow() }.catch { emit(false) }

	fun authenticate(tokenPair: AuthenticationTokenPair): Deferred<AuthenticationResult> = coroutineScope.async {
		val res = _controllerState.value?.authenticate(tokenPair) ?: return@async AuthenticationResult.Err(
			AuthenticationResult.Reason.Other
		)
		res
	}


}