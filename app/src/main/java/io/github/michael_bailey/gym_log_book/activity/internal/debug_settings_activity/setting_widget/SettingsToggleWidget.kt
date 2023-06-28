package io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity.setting_widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun SettingsToggleWidget(
	modifier: Modifier? = null,
	name: String,
	state: Boolean,
	enabled: Boolean = true,
	onChange: (Boolean) -> Unit,
) {
	Row(
		modifier ?: Modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(name, fontSize = 22.sp)
		Switch(checked = state, onCheckedChange = onChange, enabled = enabled)
	}
}

@Preview
@Composable
fun SettingsToggleWidgetPreview() {
	var state by remember { mutableStateOf(true) }
	SettingsToggleWidget(name = "Preview Widget", state = state) { state = it }
}
