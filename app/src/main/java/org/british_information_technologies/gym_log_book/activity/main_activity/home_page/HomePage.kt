package org.british_information_technologies.gym_log_book.activity.main_activity.home_page

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.british_information_technologies.gym_log_book.R
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivity
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivityViewModel
import org.british_information_technologies.gym_log_book.activity.main_activity.Menu
import org.british_information_technologies.gym_log_book.activity.main_activity.home_page.exercise_types_tab.ExerciseTypeListPage
import org.british_information_technologies.gym_log_book.activity.main_activity.home_page.exercises_tab.ExerciseListPage
import org.british_information_technologies.gym_log_book.activity.main_activity.home_page.home_tab.HomeTabView
import org.british_information_technologies.gym_log_book.activity.main_activity.weight_page.WeightListPage
import org.british_information_technologies.gym_log_book.lib.componenets.scaffold.IntentFab
import org.british_information_technologies.gym_log_book.lib.componenets.scaffold.NavigationFab

private enum class SelectedPage {
	Home,
	Exercises,
	Types,
	Weights,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage() {

	val activity = LocalContext.current as Activity
	val viewModel = hiltViewModel<MainActivityViewModel>()

	var page by remember {
		mutableStateOf(SelectedPage.Home)
	}

	val (dropDownDisplayed, setDropDownDisplayed) = remember {
		mutableStateOf(
			false
		)
	}

	val scrollState = TopAppBarDefaults.pinnedScrollBehavior()

	Scaffold(
		modifier = Modifier.nestedScroll(scrollState.nestedScrollConnection),
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
		content = { padding ->
			AnimatedContent(
				modifier = Modifier.padding(padding),
				targetState = page
			) { page ->
				when (page) {
					SelectedPage.Home -> HomeTabView()
					SelectedPage.Exercises -> ExerciseListPage(vm = viewModel)
					SelectedPage.Types -> ExerciseTypeListPage(vm = viewModel)
					SelectedPage.Weights -> WeightListPage(vm = viewModel)
				}
			}

		},
		floatingActionButton = {
			AnimatedContent(
				targetState = page, label = ""
			) { page ->
				when (page) {
					SelectedPage.Home -> IntentFab(
						label = stringResource(id = R.string.start_exercise_guide_fab_string),
						icon = Icons.AutoMirrored.Outlined.DirectionsRun,
						shownRoute = "exercise_page",
						intent = Intent(activity, ExerciseSetGuideActivity::class.java)
					)

					SelectedPage.Exercises -> IntentFab(
						label = stringResource(id = R.string.start_exercise_guide_fab_string),
						icon = Icons.AutoMirrored.Outlined.DirectionsRun,
						shownRoute = "exercise_page",
						intent = Intent(activity, ExerciseSetGuideActivity::class.java)
					)

					SelectedPage.Types -> NavigationFab(
						label = stringResource(id = R.string.add_exercise_type_fab_string),
						icon = Icons.Outlined.Add,
						route = "add_exercise_type_dialogue"
					)

					SelectedPage.Weights -> NavigationFab(
						label = stringResource(id = R.string.add_weight_fab_string),
						icon = Icons.Outlined.Add,
						route = "add_weight_dialogue"
					)
				}
			}

		},
		bottomBar = {
			NavigationBar {
				NavigationBarItem(
					selected = page == SelectedPage.Home,
					onClick = { page = SelectedPage.Home },
					label = {
						Text(
							text = stringResource(R.string.home_page_nav_button_label)
						)
					},
					icon = {
						Icon(
							imageVector = Icons.Outlined.Home, contentDescription = ""
						)
					},
				)

				NavigationBarItem(
					selected = page == SelectedPage.Exercises,
					onClick = { page = SelectedPage.Exercises },
					label = {
						Text(
							text = stringResource(R.string.exercise_page_nav_button_label)
						)
					},
					icon = {
						Icon(
							imageVector = Icons.AutoMirrored.Outlined.List,
							contentDescription = ""
						)
					},
				)

				NavigationBarItem(
					selected = page == SelectedPage.Types,
					onClick = { page = SelectedPage.Types },
					label = {
						Text(
							text = stringResource(R.string.exercise_type_page_nav_button_label)
						)
					},
					icon = {
						Icon(
							imageVector = Icons.Outlined.GridView, contentDescription = ""
						)
					},
				)

				NavigationBarItem(
					selected = page == SelectedPage.Weights,
					onClick = { page = SelectedPage.Weights },
					label = {
						Text(
							text = stringResource(R.string.weight_page_nav_button_label)
						)
					},
					icon = {
						Icon(
							imageVector = Icons.Outlined.MonitorWeight,
							contentDescription = ""
						)
					},
				)
			}

		}
	)
}