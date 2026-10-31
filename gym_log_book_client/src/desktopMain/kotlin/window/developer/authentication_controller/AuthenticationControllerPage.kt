package net.michael_bailey.gym_log_book.client.window.developer.authentication_controller

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

@Composable
fun AuthenticationControllerPage(
	viewModel: AuthenticationControllerPageViewModel = koinInject(),
) {

	val accessTokenState = remember { TextFieldState() }
	val refreshTokenState = remember { TextFieldState() }

	val isAuthenticated by viewModel.isAuthenticated.collectAsState(false)

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
					state = accessTokenState
				)
				OutlinedTextField(
					state = refreshTokenState
				)
				Row {
					Button(onClick = {
						viewModel.authenticate(
							accessTokenState.text.toString(),
							refreshTokenState.text.toString()
						)
					}) {
						Text("Authenticate!")
					}
					Button(onClick = { viewModel.reset() }) {
						Text("reset")
					}
				}
			}
		}

		if (isAuthenticated) {
			Text("Authenticated!")
		}

		if (errorMessage != null) {
			Text("Error message: $errorMessage")
		}
	}
}