package org.british_information_technologies.gym_log_book.lib.componenets.scaffold

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import org.british_information_technologies.gym_log_book.lib.navigation.navigate

@Composable
fun NavigationButton(
	route: String,
	label: String,
	icon: ImageVector? = null,
) {
	val navigate by navigate(route)

	Button(
		onClick = { navigate() },
	) {
		icon?.let { Icon(imageVector = it, contentDescription = "") }
		Text(text = label)
	}
}