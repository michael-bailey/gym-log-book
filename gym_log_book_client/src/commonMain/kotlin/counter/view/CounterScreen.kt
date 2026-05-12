package net.michael_bailey.gym_log_book.client.counter.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.michael_bailey.gym_log_book.client.counter.viewmodel.CounterViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CounterScreen(
	viewModel: CounterViewModel = koinViewModel()
) {

	val counterText by viewModel.counterText().collectAsState()

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
