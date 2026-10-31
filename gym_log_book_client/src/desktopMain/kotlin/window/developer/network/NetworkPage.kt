package net.michael_bailey.gym_log_book.client.window.developer.network

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject

@Composable
fun NetworkPage(
	viewModel: NetworkPageViewModel = koinInject(),
) {

	val statusText by viewModel.statusText.collectAsState("Disconnected")

	Column {
		Text(statusText)
	}

}