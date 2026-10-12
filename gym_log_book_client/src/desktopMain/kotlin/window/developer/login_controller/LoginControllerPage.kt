package net.michael_bailey.gym_log_book.client.window.developer.login_controller

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

@Composable
fun LoginControllerPage(
	viewModel: LoginControllerPageViewModel = koinInject()
) {

	val usernameState = remember { TextFieldState() }
	val passwordState = remember { TextFieldState() }

	val hasToken by viewModel.hasToken.collectAsState(false)

	val accessToken by viewModel.accessToken.collectAsState("")
	val refreshToken by viewModel.refreshToken.collectAsState("")

	val errorMessage by viewModel.currentExceptionMessage.collectAsState(null)

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
					state = usernameState
				)
				OutlinedTextField(
					state = passwordState
				)
				Row {
					Button(onClick = { viewModel.login(usernameState.text.toString(), passwordState.text.toString()) }) {
						Text("Get Token")
					}
					Button(onClick = { viewModel.reset() }) {
						Text("reset")
					}
				}
			}
		}

		if (hasToken) {
			Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
				TextField(value = "access: $accessToken", onValueChange = {}, readOnly = true)
				TextField(value = "refresh: $refreshToken", onValueChange = {}, readOnly = true)
			}
		}

		if (errorMessage != null) {
			Text("Error message: $errorMessage")
		}
	}
}