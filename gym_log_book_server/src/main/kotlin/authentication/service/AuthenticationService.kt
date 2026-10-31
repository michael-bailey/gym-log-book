package net.michael_bailey.gym_log_book.server.authentication.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.server.authentication.model.TokenPayload
import net.michael_bailey.gym_log_book.server.di.RpcScope
import org.koin.core.annotation.Scope
import org.koin.core.annotation.Scoped

@Scoped
@Scope(RpcScope::class)
class AuthenticationService {

	private val _currentAuthenticationToken =
		MutableStateFlow<TokenPayload?>(null)

	val isAuthenticated: Flow<Boolean> = _currentAuthenticationToken.map {
		it != null
	}

	suspend fun authenticate(accessToken: TokenPayload) {
		_currentAuthenticationToken.emit(accessToken)
	}
}
