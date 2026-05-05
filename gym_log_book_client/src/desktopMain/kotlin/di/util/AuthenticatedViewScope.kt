package net.michael_bailey.gym_log_book.client.di.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.util.KoinScope
import net.michael_bailey.gym_log_book.client.util.rememberKoinScope
import net.michael_bailey.gym_log_book.client.window.developer.DeveloperWindowViewModel.DevTab
import net.michael_bailey.gym_log_book.client.window.developer.entry.DevExerciseEntryTabPage
import net.michael_bailey.gym_log_book.client.window.developer.type.DevExerciseTypeTabPage

@Composable
fun AuthenticatedViewScope(
	devTab: DevTab
) {
	val scope = rememberKoinScope<AuthenticatedScope>()

	KoinScope(scope = scope) {
		when (devTab) {
			DevTab.Type -> DevExerciseTypeTabPage()
			DevTab.Entry -> DevExerciseEntryTabPage()
			DevTab.Login -> Text("Login Page (WIP)")
			else -> Text("WHAT HAVE YOU DONE YOU MORON >:(")
		}
	}
}
