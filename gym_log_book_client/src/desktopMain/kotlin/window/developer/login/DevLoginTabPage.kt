package net.michael_bailey.gym_log_book.client.window.developer.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import net.michael_bailey.gym_log_book.client.util.scopedInject

@Composable
fun DevLoginTabPage(
	viewModel: DevLoginViewModel = scopedInject()
) {

	val isLoggedIn by viewModel.isAuthenticated.collectAsState(false)

	Column(
		Modifier.fillMaxSize()
	) {
		if (isLoggedIn) {
			Text("Logged In")
			Button(onClick = { viewModel.logout() }) {
				Text("Logout")
			}
		} else {
			Text("Not Logged In")
			Button(onClick = { viewModel.login() }) {
				Text("Login")
			}
		}
	}
}