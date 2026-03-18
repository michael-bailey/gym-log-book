package net.michael_bailey.gym_log_book.client.counter.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.michael_bailey.gym_log_book.client.counter.service.CounterClientService
import net.michael_bailey.gym_log_book.client.counter.viewmodel.CounterViewModel

@Composable
fun CounterApp(
	counterClientService: CounterClientService,
) {
	val viewModel = remember(counterClientService) {
		CounterViewModel(counterClientService)
	}

	DisposableEffect(viewModel) {
		onDispose {
			viewModel.close()
		}
	}

	val counterText by viewModel.counterText().collectAsState()

	CounterScreen(counterText)
}

@Composable
fun CounterScreen(
	counterText: String,
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(24.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		OutlinedTextField(
			value = counterText,
			onValueChange = {},
			readOnly = true,
			label = {
				Text("Shared Counter")
			},
		)
	}
}
