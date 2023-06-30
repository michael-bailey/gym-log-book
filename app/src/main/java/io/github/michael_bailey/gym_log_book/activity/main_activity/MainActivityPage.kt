package io.github.michael_bailey.gym_log_book.activity.main_activity

import android.app.Activity
import androidx.annotation.StringRes
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.activity.add_exercise_type_activity.AddExerciseTypeActivityIntentUtils
import io.github.michael_bailey.gym_log_book.activity.add_weight_activity.AddWeightActivityIntentUtils
import io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivityIntentUtils

sealed class MainActivityPage(
	val route: String,
	@StringRes val resourceId: Int,
	val fabAction: (Activity.() -> Unit)? = null,
) {
	object ExercisePage : MainActivityPage(
		"exercise_page",
		R.string.exercise_page_nav_button_label,
		{ ExerciseSetGuideActivityIntentUtils.startExerciseSetGuideActivity(this) }
	)

	object WeightPage : MainActivityPage(
		"weight_page",
		R.string.weight_page_nav_button_label,
		{ AddWeightActivityIntentUtils.startAddWeightActivity(this) }
	)

	object ExerciseTypePage : MainActivityPage(
		"exercise_type_page",
		R.string.exercise_type_page_nav_button_label,
		{ AddExerciseTypeActivityIntentUtils.startAddExerciseTypeActivity(this) }
	)
}