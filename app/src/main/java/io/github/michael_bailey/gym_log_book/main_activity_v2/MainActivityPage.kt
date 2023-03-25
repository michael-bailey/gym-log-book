package io.github.michael_bailey.gym_log_book.main_activity_v2

import androidx.annotation.StringRes
import io.github.michael_bailey.gym_log_book.R

sealed class MainActivityPage(
	val route: String,
	@StringRes val resourceId: Int
) {
	object ExercisePage : MainActivityPage(
		"exercise_page",
		R.string.activity_exercise_set_guide_start_screen_nav_text
	)

	object WeightPage : MainActivityPage(
		"weight_page",
		R.string.activity_exercise_set_guide_set_screen_nav_text
	)
}