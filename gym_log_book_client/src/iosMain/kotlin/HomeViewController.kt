package net.michael_bailey.gym_log_book.client

import androidx.compose.ui.window.ComposeUIViewController
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.home.HomePage
import net.michael_bailey.gym_log_book.client.util.KoinScope
import net.michael_bailey.gym_log_book.client.util.rememberKoinScope

fun create() = ComposeUIViewController {

	val scope = rememberKoinScope<AuthenticatedScope>()

	KoinScope(scope = scope) {
		HomePage()
	}
}

fun create(scope: AuthenticatedScope) = ComposeUIViewController {
	KoinScope(scope = scope.scope) {
		HomePage()
	}
}