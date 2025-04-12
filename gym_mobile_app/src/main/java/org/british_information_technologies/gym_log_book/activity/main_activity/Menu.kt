package org.british_information_technologies.gym_log_book.activity.main_activity

import android.content.Intent
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.british_information_technologies.gym_log_book.activity.internal.debug_settings_activity.DebugSettingsActivity
import org.british_information_technologies.gym_log_book.activity.internal.tasks_activity.TaskActivity
import org.british_information_technologies.gym_log_book.activity.settings_activity.SettingsActivityIntentUtils
import org.british_information_technologies.gym_log_book.extension.activity
import org.british_information_technologies.gym_log_book.extension.startExportDataActivity

@Composable
fun Menu(
	expanded: Boolean,
	onDismiss: () -> Unit
) {

	val activity = activity<MainActivity>()


	DropdownMenu(
		expanded = expanded,
		onDismissRequest = onDismiss
	) {
		DropdownMenuItem(
			text = { Text(text = "Debug Settings") },
			onClick = {
				activity.startActivity(
					Intent(
						activity.applicationContext,
						DebugSettingsActivity::class.java
					)
				)
			}
		)
		DropdownMenuItem(
			text = { Text(text = "Export...") },
			onClick = {
				activity.startExportDataActivity()
			}
		)
		DropdownMenuItem(
			text = { Text(text = "Settings") },
			onClick = {
				SettingsActivityIntentUtils.startSettingsActivity(activity)
			}
		)
		DropdownMenuItem(
			text = { Text(text = "Tasks") },
			onClick = {
				activity.startActivity(
					Intent(
						activity.applicationContext,
						TaskActivity::class.java
					)
				)
			}
		)
	}
}