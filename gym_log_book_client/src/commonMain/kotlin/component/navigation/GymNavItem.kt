package net.michael_bailey.gym_log_book.client.component.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class GymNavItem<T>(
	val icon: ImageVector,
	val label: String,
	val route: T,
)