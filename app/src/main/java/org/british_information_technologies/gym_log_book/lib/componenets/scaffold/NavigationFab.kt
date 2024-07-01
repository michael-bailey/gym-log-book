package org.british_information_technologies.gym_log_book.lib.componenets.scaffold

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import org.british_information_technologies.gym_log_book.lib.navigation.NavLocal
import org.british_information_technologies.gym_log_book.lib.navigation.navigate

@Composable
fun NavigationFab(
	label: String,
	icon: ImageVector,
	route: String,
) {
	val nav = NavLocal.current
	val backstack by nav!!.currentBackStackEntryAsState()

	val navigate by navigate(route)

	ExtendedFloatingActionButton(
		onClick = { navigate() },
	) {
		Icon(imageVector = icon, contentDescription = "")
		Text(text = label)
	}
}