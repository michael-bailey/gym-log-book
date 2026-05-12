package net.michael_bailey.gym_log_book.client.component.navigation

import androidx.compose.ui.graphics.vector.ImageVector

@NavRailDsl
class GymNavScope<T> {

	// Internal mutable list — callers can't touch this directly
	internal val items = mutableListOf<GymNavItem<T>>()

	/** Add a destination to the nav rail. */
	fun item(
		icon: ImageVector,
		label: String,
		route: T,
	) {
		items += GymNavItem(
			icon = icon,
			label = label,
			route = route,
		)
	}

	/** Convenience overload — add a pre-built NavRailItem directly. */
	fun item(item: GymNavItem<T>) {
		items += item
	}
}