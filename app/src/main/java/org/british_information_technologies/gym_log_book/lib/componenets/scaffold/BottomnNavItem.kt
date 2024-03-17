package org.british_information_technologies.gym_log_book.lib.componenets.scaffold

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import org.british_information_technologies.gym_log_book.lib.navigation.NavLocal
import org.british_information_technologies.gym_log_book.lib.navigation.navigateBottom

@Composable
fun RowScope.BottomNavItem(
	label: String,
	icon: ImageVector,
	route: String
) {
	val nav = NavLocal.current
	val backstack by nav!!.currentBackStackEntryAsState()
	val isSelected by
	remember {
		derivedStateOf {
			backstack?.destination?.hierarchy?.any { it.route == route } == true
		}
	}

	val navigate by navigateBottom(route)

	NavigationBarItem(
		selected = isSelected,
		onClick = { navigate() },
		icon = { Icon(imageVector = icon, contentDescription = "") },
		label = { Text(text = label) }
	)
}