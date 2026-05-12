package net.michael_bailey.gym_log_book.client.window.developer.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.util.KoinScope
import net.michael_bailey.gym_log_book.client.util.scopedInject
import net.michael_bailey.gym_log_book.shared.authentication.controller.ViewerContextDebuggerController
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun DevLoginTabPage(
	viewModel: DevLoginViewModel = scopedInject()
) {

	val isLoggedIn by viewModel.isAuthenticated.collectAsState(false)

	Column(
		Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.spacedBy(12.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		Card(
			shape = RoundedCornerShape(24.dp)
		) {
			Column(
				modifier = Modifier.padding(24.dp),
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				OutlinedTextField(
					state = viewModel.username
				)
				OutlinedTextField(
					state = viewModel.password
				)
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

		if (isLoggedIn) {
			KoinScope<AuthenticatedScope> {
				val controller = scopedInject<ViewerContextDebuggerController>()

				var a by remember {
					mutableStateOf("")
				}

				var b by remember {
					mutableStateOf("")
				}

				val scope = rememberCoroutineScope()

				scope.launch {
					@OptIn(ExperimentalUuidApi::class)
					a = controller.getUserId().toString()
					b = controller.getUserName()
				}

				Column {
					Text(text = a)
					Text(text = b)
				}
			}
		}
	}
}