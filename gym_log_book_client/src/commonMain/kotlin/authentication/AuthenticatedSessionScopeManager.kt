@file:OptIn(kotlin.uuid.ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.authentication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import org.koin.core.Koin
import org.koin.core.scope.Scope
import kotlin.uuid.Uuid

class AuthenticatedSessionScopeManager(
	private val authenticationService: AuthenticationService,
	private val appScope: CoroutineScope,
	private val koin: Koin,
) {
	private val currentScope = MutableStateFlow<Scope?>(null)
	val scope: StateFlow<Scope?> = currentScope.asStateFlow()

	init {
		appScope.launch {
			authenticationService.isAuthenticated
				.distinctUntilChanged()
				.collect(::updateScopeForAuthenticationState)
		}
	}

	private fun updateScopeForAuthenticationState(
		isAuthenticated: Boolean,
	) {
		if (isAuthenticated) {
			ensureScope()
		} else {
			closeScope()
		}
	}

	private fun ensureScope() {
		if (currentScope.value != null) {
			return
		}

		currentScope.value = koin.createScope<AuthenticatedScope>(
			scopeId = "authenticated-session-${Uuid.random()}",
		)
	}

	private fun closeScope() {
		currentScope.value?.close()
		currentScope.value = null
	}
}
