package net.michael_bailey.gym_log_book.client.component.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T : Any> GymNavigationRail(
	currentRoute: T,
	onNavigate: (T) -> Unit,
	fab: FabDefinition? = null,
	content: GymNavScope<T>.() -> Unit,
) {
	val scope = GymNavScope<T>().apply(content)
	GymNavigationRail(
		currentRoute = currentRoute, onNavigate = onNavigate, fab = fab, scope = scope
	)
}


@Composable
fun <T : Any> GymNavigationRail(
	currentRoute: T,
	fab: FabDefinition? = null,
	onNavigate: (T) -> Unit,
	scope: GymNavScope<T>,
) {
	NavigationRail(
		header = fab?.let(::defineHeader)
	) {
		scope.items.forEach { item ->
			NavigationRailItem(
				selected = currentRoute == item.route,
				onClick = { onNavigate(item.route) },
				icon = { Icon(item.icon, contentDescription = item.label) },
				label = { Text(item.label) },
				// badge handling, etc.
			)
		}
	}
}

private fun defineHeader(fab: FabDefinition): @Composable ColumnScope.() -> Unit = {
	FloatingActionButton(
		modifier = Modifier.padding(top = 8.dp),
		onClick = fab.onClick,
	) {
		Icon(
			fab.icon,
			// TODO:  add proper content descriptions
			contentDescription = "Floating action button"
		)
	}
}

