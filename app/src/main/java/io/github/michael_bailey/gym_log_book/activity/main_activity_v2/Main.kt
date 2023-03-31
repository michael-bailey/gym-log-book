package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import io.github.michael_bailey.gym_log_book.activity.debug_settings_activity.DebugSettingsActivity
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.exercise_page.ExerciseListPage
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.weight_page.WeightListPage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Main(vm: MainActivityV2ViewModel) {
	val activity = LocalContext.current as Activity
	val items = listOf(
		MainActivityPage.ExercisePage,
		MainActivityPage.WeightPage,
		MainActivityPage.ExerciseTypePage,
	)
	val nav = rememberNavController()
	val navBackStackEntry by nav.currentBackStackEntryAsState()

	val fabExpanded by remember { mutableStateOf(true) }

	var dropDownDisplayed by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			SmallTopAppBar(
				{ Text(text = stringResource(R.string.app_name)) },
				actions = {
					IconButton(onClick = { dropDownDisplayed = !dropDownDisplayed }) {
						Icon(Icons.Default.MoreVert, "content")
					}
					DropdownMenu(
						expanded = dropDownDisplayed,
						onDismissRequest = { dropDownDisplayed = false }
					) {
						DropdownMenuItem(
							text = { Text(text = "Settings") },
							onClick = {
								activity.startActivity(
									Intent(
										activity.applicationContext,
										DebugSettingsActivity::class.java
									)
								)
							}
						)
					}

				}
			)
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
							ExerciseListPage(vm)
						}
					}
					composable(MainActivityPage.WeightPage.route) {
						Column(
							Modifier.fillMaxWidth(),
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							WeightListPage(vm)
						}
					}
					composable(MainActivityPage.ExerciseTypePage.route) {
						Column(
							Modifier.fillMaxWidth(),
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							ExerciseTypeListPage(vm)
						}
					}
				}
			}
		},
		floatingActionButton = {
			ExtendedFloatingActionButton(
				onClick = {
					MainActivityUtils.openSetGuideActivity(
						activity
					)
				},
			) {
				Icon(
					imageVector = Icons.Filled.Add,
					contentDescription = "Add Set Button"
				)
				Text(text = navBackStackEntry?.let { getOnClickText(backStack = it) }
					?: "ERR")

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
								// Pop up to the start destination of the graph to
								// avoid building up a large stack of destinations
								// on the back stack as users select items
								popUpTo(nav.graph.findStartDestination().id) {
									saveState = true
								}
								// Avoid multiple copies of the same destination when
								// reselecting the same item
								launchSingleTop = true
								// Restore state when reselecting a previously selected item
								restoreState = true
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