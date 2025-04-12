package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.british_information_technologies.gym_log_book.R
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.SetGuideViewModelV2

sealed class ExerciseSetGuideActivityPage<VM : ViewModel>(
	val route: String,
	@StringRes val resourceId: Int,
	val content: @Composable (vm: VM) -> Unit,
) {
	object Start : ExerciseSetGuideActivityPage<SetGuideViewModelV2>(
		"start_page",
		R.string.activity_exercise_set_guide_start_screen_nav_text,
		{ vm -> StartPage(vm) }
	)

	object Set : ExerciseSetGuideActivityPage<SetGuideViewModelV2>(
		"set_page",
		R.string.activity_exercise_set_guide_set_screen_nav_text,
		{ vm -> SetPage(vm) }
	)

	object Pause : ExerciseSetGuideActivityPage<SetGuideViewModelV2>(
		"pause_page",
		R.string.activity_exercise_set_guide_pause_screen_nav_text,
		{ vm -> PausePage(vm) }
	)

	object AskExtraSet : ExerciseSetGuideActivityPage<SetGuideViewModelV2>(
		"ask_extra_set",
		R.string.activity_exercise_set_guide_ask_extra_set_screen_nav_text,
		{ vm -> AskExtraSetPage(vm) }
	)
}