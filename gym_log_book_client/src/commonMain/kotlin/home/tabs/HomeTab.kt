package net.michael_bailey.gym_log_book.client.home.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class HomeTab(
	val icon: ImageVector,
	val label: String,
) {
	Exercises(icon = Icons.Default.Home, label = "Exercises"),
	Types(icon = Icons.Default.Home, label = "Types"),
}