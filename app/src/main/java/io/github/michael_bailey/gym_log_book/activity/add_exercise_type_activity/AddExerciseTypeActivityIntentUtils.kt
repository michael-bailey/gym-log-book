package io.github.michael_bailey.gym_log_book.activity.add_exercise_type_activity

import android.app.Activity
import android.content.Context
import android.content.Intent

object AddExerciseTypeActivityIntentUtils {
	fun startAddExerciseTypeActivity(ctx: Context): Intent =
		Intent(ctx, AddExerciseTypeActivity::class.java).apply {
			(ctx as Activity).startActivity(this)
		}
}