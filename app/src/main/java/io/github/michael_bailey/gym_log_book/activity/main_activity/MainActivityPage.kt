package io.github.michael_bailey.gym_log_book.activity.main_activity


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.michael_bailey.android_chat_kit.utils.page.FabConfig
import io.github.michael_bailey.android_chat_kit.utils.page.PageNavigation
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.activity.main_activity.exercise_page.ExerciseListPage
import io.github.michael_bailey.gym_log_book.activity.main_activity.weight_page.WeightListPage

sealed class MainActivityPage(
	route: String,
	label: Int,
	icon: ImageVector
) : PageNavigation<MainActivityViewModel>(route, label, icon) {
	object ExercisePage : MainActivityPage(
		"exercise_page",
		R.string.exercise_page_nav_button_label,
		Icons.Outlined.List
	) {
		override val pageFunction: @Composable (MainActivityViewModel) -> Unit
			get() = {
				ExerciseListPage(vm = it)
			}
		override val fab
			get() = FabConfig<MainActivityViewModel>(
				label = R.string.exercise_page_nav_button_label,
				icon = Icons.Outlined.Add,
				exec = {
					it.startExerciseSetGuideActivity()
				}
			)
	}

	object WeightPage : MainActivityPage(
		route = "weight_page",
		label = R.string.weight_page_nav_button_label,
		icon = Icons.Outlined.Menu,
	) {
		override val pageFunction: @Composable (MainActivityViewModel) -> Unit
			get() = { WeightListPage(vm = it) }
		override val fab
			get() = FabConfig<MainActivityViewModel>(
				label = R.string.weight_page_nav_button_label,
				icon = Icons.Outlined.Add,
				exec = {
					it.startAddWeightActivity()
				}
			)
	}

	object ExerciseTypePage : MainActivityPage(
		route = "exercise_type_page",
		label = R.string.exercise_type_page_nav_button_label,
		icon = Icons.Outlined.Edit
	) {
		override val pageFunction: @Composable (MainActivityViewModel) -> Unit
			get() = { ExerciseTypeListPage(vm = it) }

		override val fab: FabConfig<MainActivityViewModel>
			get() = FabConfig(
				label = R.string.exercise_type_page_nav_button_label,
				icon = Icons.Outlined.Edit,
				exec = {
					it.startAddExerciseTypeActivity()
				}
			)
	}
}