package org.british_information_technologies.gym_log_book.extension

import android.app.Activity
import android.content.Intent
import org.british_information_technologies.gym_log_book.activity.data_export.DataExportActivity

fun Activity.startExportDataActivity() {
	startActivity(
		Intent(this, DataExportActivity::class.java)
	)
}