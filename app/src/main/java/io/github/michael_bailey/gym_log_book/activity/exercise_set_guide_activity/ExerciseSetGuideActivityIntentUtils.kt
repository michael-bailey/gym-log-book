package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.util.UUID

object ExerciseSetGuideActivityIntentUtils {
	const val EXERCISE_ID = "exercise_id"
	const val CURRENT_SET = "current_set"
	const val CURRENT_WEIGHT = "current_weight"

	fun startExerciseSetGuideActivity(ctx: Context): Intent =
		Intent(ctx, ExerciseSetGuideActivity::class.java).apply {
			(ctx as Activity).startActivity(this)
		}

	fun createExerciseSetGuideActivityIntent(
		ctx: Context,
		exerciseTypeID: UUID,
		currentSet: Int,
		currentWeight: Double,
	): Intent =
		Intent(ctx, ExerciseSetGuideActivity::class.java).apply {
			putExtra(EXERCISE_ID, exerciseTypeID)
			putExtra(CURRENT_SET, currentSet)
			putExtra(CURRENT_WEIGHT, currentWeight)
		}
}