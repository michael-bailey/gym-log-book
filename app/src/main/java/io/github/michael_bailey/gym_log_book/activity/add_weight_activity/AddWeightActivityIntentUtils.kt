package io.github.michael_bailey.gym_log_book.activity.add_weight_activity

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK

object AddWeightActivityIntentUtils {
	fun startAddWeightActivity(ctx: Context): Intent =
		Intent(ctx, AddWeightActivity::class.java).apply {
			flags = FLAG_ACTIVITY_NEW_TASK
			ctx.startActivity(this)
		}
}