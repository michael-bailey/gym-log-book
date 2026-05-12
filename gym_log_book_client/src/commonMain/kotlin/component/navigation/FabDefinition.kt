package net.michael_bailey.gym_log_book.client.component.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class FabDefinition(
	val icon: ImageVector,
	val onClick: () -> Unit
)