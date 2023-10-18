package org.british_information_technologies.gym_log_book.service

import android.content.Context
import android.content.Intent

object ServiceUtils {
	fun startMigrationIntent(context: Context) {
		context.startForegroundService(
			Intent(
				context, DataImporterService::class.java
			)
		)
	}
}