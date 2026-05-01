package net.michael_bailey.gym_log_book.client.di

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import net.michael_bailey.gym_log_book.client.util.KoinScope
import net.michael_bailey.gym_log_book.client.window.developer.DeveloperWindowViewModel.DevTab
import net.michael_bailey.gym_log_book.client.window.developer.login.DevLoginTabPage

@Composable
fun AuthenticationViewScope(
	devTab: DevTab
) {
	KoinScope<AuthenticationScope> {
		when (devTab) {
			DevTab.Login -> DevLoginTabPage()
			else -> Text("WHAT HAVE YOU DONE YOU MORON >:(")
		}
	}
}