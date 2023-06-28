package io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity

import android.app.Activity
import android.content.Context
import android.content.Intent

object AmendExerciseActivityIntentUtils {
	fun startAmendActivity(ctx: Context, exerciseID: Int): Intent =
		Intent(ctx, AmendExerciseActivity::class.java).apply {
			putExtra("exercise_id", exerciseID)
			(ctx as Activity).startActivity(this)
		}
}