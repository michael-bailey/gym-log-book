package org.british_information_technologies.gym_log_book.activity.data_export

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.window.Dialog
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme

class DataExportActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Gym_Log_BookTheme {
				Dialog(onDismissRequest = { finish() }) {
					Main("Android")
				}
			}
		}
	}
}

