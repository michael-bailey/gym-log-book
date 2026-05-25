package net.michael_bailey.gym_log_book.client.authentication.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.michael_bailey.gym_log_book.client.authentication.state.LoginViewState

@Composable
fun LoginFormCard(
	modifier: Modifier = Modifier,
	state: LoginViewState,
	onSubmit: () -> Unit,
	onCancel: () -> Unit,
) {
	Card(
		modifier = modifier.width(IntrinsicSize.Min).height(IntrinsicSize.Min)
	) {
		Column(
			modifier = Modifier.padding(21.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp),
		) {

			var isPasswordShown by state.isPasswordShownState
			val textObfuscation by state.textObfuscationState

			OutlinedTextField(

				state = state.usernameFieldState,
				label = { Text("Username") }
			)
			OutlinedSecureTextField(

				state = state.passwordFieldState,
				label = { Text("Password") },
				textObfuscationMode = textObfuscation
			)
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Checkbox(
					checked = isPasswordShown,
					onCheckedChange = { isPasswordShown = it }
				)
				Text("Show Password")
			}
			Row(
				modifier = modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				OutlinedButton(onClick = onCancel) {
					Text("Cancel")
				}
				Button(onClick = onSubmit) {
					Text("Submit")
				}
			}
		}
	}
}

@Preview
@Composable
fun LoginFormCard_Preview() {
	val loginViewState = remember { LoginViewState() }
	LoginFormCard(
		state = loginViewState,
		onSubmit = {},
		onCancel = {}
	)
}