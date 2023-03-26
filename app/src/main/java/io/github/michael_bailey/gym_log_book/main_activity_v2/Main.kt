package io.github.michael_bailey.gym_log_book.main_activity_v2

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.michael_bailey.gym_log_book.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: MainActivityV2ViewModel) {
	val items = listOf(
		MainActivityPage.ExercisePage,
		MainActivityPage.WeightPage,
		MainActivityPage.ExerciseTypePage,
	)
	val nav = rememberNavController()
	Scaffold(
		topBar = {
			SmallTopAppBar({ Text(text = stringResource(R.string.app_name)) })
		},
		content = {
			Surface(Modifier.padding(it)) {
				NavHost(
					navController = nav,
					startDestination = MainActivityPage.ExercisePage.route
				) {
					composable(MainActivityPage.ExercisePage.route) {
						ExerciseListPage(vm)
					}
					composable(MainActivityPage.WeightPage.route) {
						WeightListPage(vm)
					}
					composable(MainActivityPage.ExerciseTypePage.route) {
						ExerciseTypeListPage(vm)
					}
				}
			}
		},
		bottomBar = {
			NavigationBar {
				val navBackStackEntry by nav.currentBackStackEntryAsState()
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