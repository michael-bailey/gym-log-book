package io.github.michael_bailey.gym_log_book.activity.debug_settings_activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Main(vm: DebugSettingsViewModel) {

	val state = remember {
		mutableStateOf(vm.exerciseListState)
	}

	LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
		item {
			Row(
				Modifier.fillMaxSize(0.9f),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text("Debug Nav Bars")
				Switch(checked = state.value, onCheckedChange = vm::setIsDebugEnabled)
			}
		}
	}
}