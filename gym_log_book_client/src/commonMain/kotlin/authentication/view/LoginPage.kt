package net.michael_bailey.gym_log_book.client.authentication.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.michael_bailey.gym_log_book.client.authentication.state.LoginViewState
import net.michael_bailey.gym_log_book.client.authentication.view_model.LoginPageViewModel
import org.koin.compose.koinInject

@Composable
fun LoginPage(
	viewModel: LoginPageViewModel = koinInject()
) {
	LoginPage(
		loginViewState = viewModel.loginFormState,
		onSubmit = viewModel::submit,
		onCancel = viewModel::cancel
	)
}

@Composable
fun LoginPage(
	loginViewState: LoginViewState,
	onSubmit: () -> Unit,
	onCancel: () -> Unit,
) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		LoginFormCard(
			modifier = Modifier.padding(12.dp),
			state = loginViewState,
			onSubmit = onSubmit,
			onCancel = onCancel
		)
	}
}

@Preview
@Composable
fun LoginPage_Preview() {
	val loginViewState = remember { LoginViewState() }

	LoginPage(loginViewState, {}, {})
}