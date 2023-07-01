package io.github.michael_bailey.gym_log_book.activity.main_activity

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity.DebugSettingsActivity
import io.github.michael_bailey.gym_log_book.activity.internal.tasks_activity.TaskActivity
import io.github.michael_bailey.gym_log_book.activity.main_activity.exercise_page.ExerciseListPage
import io.github.michael_bailey.gym_log_book.activity.main_activity.weight_page.WeightListPage
import io.github.michael_bailey.gym_log_book.extension.any.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: MainActivityViewModel) {
	val activity = LocalContext.current as Activity


	val window = activity.window

	val items = listOf(
		MainActivityPage.ExercisePage,
		MainActivityPage.WeightPage,
		MainActivityPage.ExerciseTypePage,
	)

	val nav = rememberNavController()
	val listState = rememberLazyListState()

	val (dropDownDisplayed, setDropDownDisplayed) = remember {
		mutableStateOf(
			false
		)
	}
	val (fabAction, setFabAction) = remember {
		mutableStateOf(
			MainActivityPage.ExercisePage.fabAction ?: {
				log("Fab has no action")
			}
		)
	}

	val scrollState = TopAppBarDefaults.pinnedScrollBehavior()


	val navBackStackEntry by nav.currentBackStackEntryAsState()

	val fabVisibility by remember {
		derivedStateOf {
			listState.firstVisibleItemIndex == 0
		}
	}

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
					composable(MainActivityPage.ExercisePage.route) {

						Column(
							Modifier.fillMaxWidth(),
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							ExerciseListPage(vm, listState)
						}
					}
					composable(MainActivityPage.WeightPage.route) {
						Column(
							Modifier.fillMaxWidth(),
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							WeightListPage(vm, listState)
						}
					}
					composable(MainActivityPage.ExerciseTypePage.route) {
						Column(
							Modifier.fillMaxWidth(),
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							ExerciseTypeListPage(vm, listState)
						}
					}
				}
			}
		},
		floatingActionButton = {
			ExtendedFloatingActionButton(
				onClick = { activity.fabAction() },
			) {
				Icon(
					imageVector = Icons.Filled.Add,
					contentDescription = "Add Set Button"
				)
				if (fabVisibility) {
					Text(text = navBackStackEntry?.let { getOnClickText(backStack = it) }
						?: "ERR")
				}
			}
		},
		bottomBar = {
			NavigationBar {
				val currentDestination = navBackStackEntry?.destination
				items.forEach { page ->
					NavigationBarItem(
						icon = {
							Icon(
								Icons.Filled.Settings,
								contentDescription = null
							)
						},
						label = { Text(stringResource(page.resourceId)) },
						selected = currentDestination?.hierarchy?.any { it.route == page.route } == true,
						onClick = {
							nav.navigate(page.route) {
								popUpTo(nav.graph.findStartDestination().id) {
									saveState = true
								}
								launchSingleTop = true
								restoreState = true
								setFabAction(page.fabAction ?: { log("Fab has no action") })
							}
						}
					)
				}
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