package net.michael_bailey.gym_log_book.client.component.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun <T> GymNavigationBar(
	currentRoute: T,
	onNavigate: (T) -> Unit,
	content: GymNavScope<T>.() -> Unit,
) {

	val scope = GymNavScope<T>().apply(content)
	GymNavigationBar(
		currentRoute = currentRoute,
		onNavigate = onNavigate,
		scope = scope
	)
}

@Composable
fun <T> GymNavigationBar(
	currentRoute: T,
	onNavigate: (T) -> Unit,
	scope: GymNavScope<T>,
) {
	NavigationBar {
		scope.items.forEach { item ->
			NavigationBarItem(
				selected = currentRoute == item.route,
				onClick = { onNavigate(item.route) },
				icon = { Icon(item.icon, contentDescription = item.label) },
				label = { Text(item.label) },
			)
		}
	}
}
