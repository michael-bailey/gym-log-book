package org.british_information_technologies.gym_log_book.lib.componenets.scaffold

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import org.british_information_technologies.gym_log_book.lib.navigation.NavLocal
import org.british_information_technologies.gym_log_book.lib.navigation.navigate

@Composable
fun NavigationFab(
	label: String,
	icon: ImageVector,
	shownRoute: String,
	route: String,
) {
	val nav = NavLocal.current
	val backstack by nav!!.currentBackStackEntryAsState()
	val isShown by remember {
		derivedStateOf {
			backstack?.destination?.route == shownRoute
		}
	}

	val navigate by navigate(route)

	if (!isShown) {
		return
	}

	ExtendedFloatingActionButton(
		onClick = { navigate() },
	) {
		Icon(imageVector = icon, contentDescription = "")
		Text(text = label)
	}
}