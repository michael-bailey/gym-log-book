package io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity.setting_widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity.DebugSettingsViewModel

@Composable
fun SettingsGatekeeperWidget(
	vm: DebugSettingsViewModel,
	gatekeeperName: String
) {

	val isEnabled by vm.isDebugEnabled.observeAsState(initial = false)
	val state by vm.evalGatekeeper(gatekeeperName).observeAsState(initial = false)

	SettingsToggleWidget(
		Modifier.fillMaxWidth(),
		name = gatekeeperName,
		state = state,
		enabled = isEnabled,
		onChange = { vm.setGatekeeper(gatekeeperName, it) }
	)
}