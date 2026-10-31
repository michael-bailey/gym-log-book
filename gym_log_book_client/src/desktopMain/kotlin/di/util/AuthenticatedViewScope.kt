package net.michael_bailey.gym_log_book.client.di.util

import androidx.compose.runtime.Composable
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.util.KoinScope
import net.michael_bailey.gym_log_book.client.util.rememberKoinScope
import net.michael_bailey.gym_log_book.client.window.developer.DeveloperWindowViewModel.DevTab

@Composable
fun AuthenticatedViewScope(
	devTab: DevTab
) {
	val scope = rememberKoinScope<AuthenticatedScope>()

	KoinScope(scope = scope) {

	}
}
