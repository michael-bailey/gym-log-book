package org.british_information_technologies.gym_log_book.lib.componenets.scaffold

import android.content.Intent
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.british_information_technologies.gym_log_book.lib.startActivity

@Composable
fun IntentFab(
	label: String,
	icon: ImageVector,
	shownRoute: String,
	intent: Intent,
) {
	val start = startActivity(intent)

	ExtendedFloatingActionButton(
		onClick = { start() },
	) {
		Icon(imageVector = icon, contentDescription = "")
		Text(text = label)
	}
}