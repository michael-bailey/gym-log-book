package net.michael_bailey.gym_log_book.client.di.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import net.michael_bailey.gym_log_book.client.di.scopes.LoginScope
import net.michael_bailey.gym_log_book.client.util.KoinScope
import net.michael_bailey.gym_log_book.client.window.developer.DeveloperWindowViewModel.DevTab
import net.michael_bailey.gym_log_book.client.window.developer.login.DevLoginTabPage

@Composable
fun LoginViewScope(
	devTab: DevTab,
) {
	KoinScope<LoginScope> {
		when (devTab) {
			DevTab.Login -> DevLoginTabPage()
			else -> Text("WHAT HAVE YOU DONE YOU MORON >:(")
		}
	}
}
