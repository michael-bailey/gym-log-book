package io.github.michael_bailey.gym_log_book.main_activity_v2

import androidx.annotation.StringRes
import io.github.michael_bailey.gym_log_book.R

sealed class MainActivityPage(
	val route: String,
	@StringRes val resourceId: Int
) {
	object ExercisePage : MainActivityPage(
		"exercise_page",
		R.string.exercise_page_nav_button_label
	)

	object WeightPage : MainActivityPage(
		"weight_page",
		R.string.weight_page_nav_button_label
	)

	object ExerciseTypePage : MainActivityPage(
		"exercise_type_page",
		R.string.exercise_type_page_nav_button_label
	)
}