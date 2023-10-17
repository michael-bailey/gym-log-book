package io.github.michael_bailey.gym_log_book.activity.main_activity

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity.DebugSettingsActivity
import io.github.michael_bailey.gym_log_book.activity.internal.tasks_activity.TaskActivity
import io.github.michael_bailey.gym_log_book.activity.settings_activity.SettingsActivityIntentUtils.startSettingsActivity
import io.github.michael_bailey.gym_log_book.activity.main_activity.MainActivityPage.ExercisePage.NavItem as ExerciseNavItem
import io.github.michael_bailey.gym_log_book.activity.main_activity.MainActivityPage.ExerciseTypePage.NavItem as ExerciseTypeNavItem
import io.github.michael_bailey.gym_log_book.activity.main_activity.MainActivityPage.WeightPage.NavItem as WeightNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: MainActivityViewModel) {
	val activity = LocalContext.current as Activity

	val nav = rememberNavController()
	val currentRoute = nav
		.currentBackStackEntryFlow
		.collectAsState(initial = nav.currentBackStackEntry)

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
					DropdownMenu(
						expanded = dropDownDisplayed,
						onDismissRequest = { setDropDownDisplayed(false) }
					) {
						DropdownMenuItem(
							text = { Text(text = "Debug Settings") },
							onClick = {
								activity.startActivity(
									Intent(
										activity.applicationContext,
										DebugSettingsActivity::class.java
									)
								)
							}
						)
						DropdownMenuItem(
							text = { Text(text = "Settings") },
							onClick = {
								startSettingsActivity(activity)
							}
						)
						DropdownMenuItem(
							text = { Text(text = "Tasks") },
							onClick = {
								activity.startActivity(
									Intent(
										activity.applicationContext,
										TaskActivity::class.java
									)
								)
							}
						)
					}
				})
		},
		content = {
			Surface(Modifier.padding(it)) {
				NavHost(
					navController = nav,
					startDestination = MainActivityPage.ExercisePage.route
				) {
					MainActivityPage.ExercisePage.compose(this, vm)
					MainActivityPage.ExerciseTypePage.compose(this, vm)
					MainActivityPage.WeightPage.compose(this, vm)
				}
			}
		},
		floatingActionButton = {
			when (currentRoute.value?.destination?.route) {
				MainActivityPage.ExercisePage.route -> MainActivityPage.ExercisePage.Fab(
					vm
				)

				MainActivityPage.WeightPage.route -> MainActivityPage.WeightPage.Fab(vm)
				MainActivityPage.ExerciseTypePage.route -> MainActivityPage.ExerciseTypePage.Fab(
					vm
				)
			}
		},
		bottomBar = {
			NavigationBar {
				this.ExerciseNavItem(nav)
				this.ExerciseTypeNavItem(nav)
				this.WeightNavItem(nav)
			}
		}
	)
}

fun getOnClickText(backStack: NavBackStackEntry): String {
	return when (backStack.destination.route) {
		MainActivityPage.ExercisePage.route -> "Add Set"
		MainActivityPage.WeightPage.route -> "Add Weight"
		MainActivityPage.ExerciseTypePage.route -> "Add type"
		else -> {
			"ERR"
		}
	}
}