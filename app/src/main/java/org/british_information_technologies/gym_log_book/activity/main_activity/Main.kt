package org.british_information_technologies.gym_log_book.activity.main_activity

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import io.github.michael_bailey.gym_log_book.activity.main_activity.ExerciseTypeListPage
import org.british_information_technologies.gym_log_book.R
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivity
import org.british_information_technologies.gym_log_book.activity.main_activity.dialogue.ExerciseEntryModifyDialogue
import org.british_information_technologies.gym_log_book.activity.main_activity.dialogue.ExerciseTypeCreateDialogue
import org.british_information_technologies.gym_log_book.activity.main_activity.dialogue.ExerciseTypeModifyDialogue
import org.british_information_technologies.gym_log_book.activity.main_activity.exercise_page.ExerciseListPage
import org.british_information_technologies.gym_log_book.activity.main_activity.weight_page.WeightListPage
import org.british_information_technologies.gym_log_book.lib.componenets.scaffold.BottomNavItem
import org.british_information_technologies.gym_log_book.lib.componenets.scaffold.IntentFab
import org.british_information_technologies.gym_log_book.lib.componenets.scaffold.NavigationFab
import org.british_information_technologies.gym_log_book.lib.navigation.NavLocal
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: MainActivityViewModel) {
	val activity = LocalContext.current as Activity
	val nav = NavLocal.current
	val (dropDownDisplayed, setDropDownDisplayed) = remember {
		mutableStateOf(
			false
		)
	}

	val scrollState = TopAppBarDefaults.pinnedScrollBehavior()

	Scaffold(
		Modifier.nestedScroll(scrollState.nestedScrollConnection),
		topBar = {
			TopAppBar(
				title = { Text(text = stringResource(R.string.app_name)) },
				scrollBehavior = scrollState,
				actions = {
					IconButton(onClick = { setDropDownDisplayed(!dropDownDisplayed) }) {
						Icon(Icons.Default.MoreVert, "content")
					}
					Menu(
						expanded = dropDownDisplayed,
						onDismiss = { setDropDownDisplayed(false) })
				})
		},
		content = {
			Surface(Modifier.padding(it)) {
				NavHost(
					navController = nav!!,
					startDestination = "exercise_page",
				) {
					composable(route = "exercise_page") { ExerciseListPage(vm = vm) }
					composable(route = "exercise_type_page") { ExerciseTypeListPage(vm = vm) }
					composable(route = "weight_page") { WeightListPage(vm = vm) }

					// dialogues
					dialog(route = "add_exercise_type_dialogue") {
						ExerciseTypeCreateDialogue(vm)
					}

					dialog(
						route = "modify_exercise_type_dialogue/{id}",
						arguments = listOf(
							navArgument(name = "id") {
								this.nullable = false
								this.type = NavType.StringType
							}
						),
					) {
						val stringID = it.arguments?.getString("id")
						if (stringID == null) {
							nav.popBackStack()
						}
						val id = UUID.fromString(stringID)
						ExerciseTypeModifyDialogue(vm = vm, id = id)
					}

					dialog(
						route = "modify_exercise_dialogue/{id}",
						arguments = listOf(
							navArgument(name = "id") {
								this.nullable = false
								this.type = NavType.StringType
							}
						),
					) {
						val stringID = it.arguments?.getString("id")
						if (stringID == null) {
							nav.popBackStack()
						}
						val id = UUID.fromString(stringID)
						ExerciseEntryModifyDialogue(vm = vm, id = id)
					}

					dialog(route = "add_weight_dialogue") {
						Text(text = "Add Weight")
					}
				}
			}
		},
		floatingActionButton = {
			IntentFab(
				label = stringResource(id = R.string.start_exercise_guide_fab_string),
				icon = Icons.AutoMirrored.Outlined.DirectionsRun,
				shownRoute = "exercise_page",
				intent = Intent(activity, ExerciseSetGuideActivity::class.java)
			)
			NavigationFab(
				label = stringResource(R.string.add_exercise_type_fab_string),
				icon = Icons.Outlined.Add,
				shownRoute = "exercise_type_page",
				route = "add_exercise_type_dialogue"
			)
			NavigationFab(
				label = stringResource(R.string.add_weight_fab_string),
				icon = Icons.Outlined.Add,
				shownRoute = "weight_page",
				route = "add_weight_dialogue"
			)
		},
		bottomBar = {
			NavigationBar {
				BottomNavItem(
					label = stringResource(R.string.exercise_page_nav_button_label),
					icon = Icons.AutoMirrored.Outlined.List,
					route = "exercise_page"
				)

				BottomNavItem(
					label = stringResource(R.string.exercise_type_page_nav_button_label),
					icon = Icons.Outlined.Edit,
					route = "exercise_type_page"
				)

				BottomNavItem(
					label = stringResource(R.string.weight_page_nav_button_label),
					icon = Icons.Outlined.MonitorWeight,
					route = "weight_page"
				)
			}
		}
	)
}