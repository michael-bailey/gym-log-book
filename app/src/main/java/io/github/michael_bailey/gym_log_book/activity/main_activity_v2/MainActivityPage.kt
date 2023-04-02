package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import android.app.Activity
import android.content.Intent
import androidx.annotation.StringRes
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.activity.add_weight_activity.AddWeightActivity
import io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivity

sealed class MainActivityPage(
	val route: String,
	@StringRes val resourceId: Int,
	val fabAction: (Activity.() -> Unit)? = null,
) {
	object ExercisePage : MainActivityPage(
		"exercise_page",
		R.string.exercise_page_nav_button_label,
		{
			startActivity(
				Intent(
					applicationContext,
					ExerciseSetGuideActivity::class.java
				)
			)
		},
	)

	object WeightPage : MainActivityPage(
		"weight_page",
		R.string.weight_page_nav_button_label,
		{ startActivity(Intent(applicationContext, AddWeightActivity::class.java)) }
	)

	object ExerciseTypePage : MainActivityPage(
		"exercise_type_page",
		R.string.exercise_type_page_nav_button_label,
	)
}