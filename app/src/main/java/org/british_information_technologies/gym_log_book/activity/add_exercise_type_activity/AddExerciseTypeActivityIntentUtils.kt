package org.british_information_technologies.gym_log_book.activity.add_exercise_type_activity

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK

object AddExerciseTypeActivityIntentUtils {
	fun startAddExerciseTypeActivity(ctx: Context): Intent =
		Intent(ctx, AddExerciseTypeActivity::class.java).apply {
			flags = FLAG_ACTIVITY_NEW_TASK
			ctx.startActivity(this)
		}
}