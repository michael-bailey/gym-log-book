package io.github.michael_bailey.gym_log_book.main_activity_v2

import android.app.Activity
import android.content.Intent
import io.github.michael_bailey.gym_log_book.exercise_set_guide_activity.ExerciseSetGuideActivity

object MainActivityUtils {
	fun openSetGuideActivity(activity: Activity) {
		activity.startActivity(
			Intent(
				activity.applicationContext,
				ExerciseSetGuideActivity::class.java
			)
		)
	}
}