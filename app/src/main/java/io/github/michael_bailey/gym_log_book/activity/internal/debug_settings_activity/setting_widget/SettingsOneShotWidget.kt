package io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity.setting_widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.lib.one_shot.OneShotPreference

@Composable
fun SettingsOneShotWidget(
	modifier: Modifier? = Modifier,
	oneShot: OneShotPreference,
) {
	Row(
		modifier ?: Modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(oneShot.getPreferenceName(), fontSize = 22.sp)
		Button(enabled = !oneShot.isConsumed(), onClick = { oneShot.reset() }) {
			if (oneShot.isConsumed()) {
				Text(text = "Reset")
			} else {
				Text("Used")
			}
		}
	}
}