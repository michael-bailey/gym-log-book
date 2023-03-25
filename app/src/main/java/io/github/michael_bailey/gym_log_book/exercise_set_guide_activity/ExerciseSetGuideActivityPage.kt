package io.github.michael_bailey.gym_log_book.exercise_set_guide_activity

import androidx.annotation.StringRes
import io.github.michael_bailey.gym_log_book.R


sealed class ExerciseSetGuideActivityPage(
	val route: String,
	@StringRes val resourceId: Int
) {
	object Start : ExerciseSetGuideActivityPage(
		"start_page",
		R.string.activity_exercise_set_guide_start_screen_nav_text
	)

	object Set : ExerciseSetGuideActivityPage(
		"set_page",
		R.string.activity_exercise_set_guide_set_screen_nav_text
	)

	object Pause : ExerciseSetGuideActivityPage(
		"pause_page",
		R.string.activity_exercise_set_guide_pause_screen_nav_text
	)

	object AskExtraSet : ExerciseSetGuideActivityPage(
		"ask_extra_set",
		R.string.activity_exercise_set_guide_ask_extra_set_screen_nav_text
	)
}