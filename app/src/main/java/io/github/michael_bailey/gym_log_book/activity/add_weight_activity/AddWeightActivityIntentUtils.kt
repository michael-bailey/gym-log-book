package io.github.michael_bailey.gym_log_book.activity.add_weight_activity

import android.app.Activity
import android.content.Context
import android.content.Intent

object AddWeightActivityIntentUtils {
	fun startAddWeightActivity(ctx: Context): Intent =
		Intent(ctx, AddWeightActivity::class.java).apply {
			(ctx as Activity).startActivity(this)
		}
}