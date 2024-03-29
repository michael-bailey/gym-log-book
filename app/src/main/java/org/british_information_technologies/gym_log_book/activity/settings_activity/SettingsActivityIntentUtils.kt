package org.british_information_technologies.gym_log_book.activity.settings_activity

import android.content.Context
import android.content.Intent

object SettingsActivityIntentUtils {
	fun startSettingsActivity(ctx: Context) {
		Intent(ctx, SettingsActivity::class.java).apply {
			ctx.startActivity(this)
		}
	}
}