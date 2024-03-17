package org.british_information_technologies.gym_log_book.lib

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import org.british_information_technologies.gym_log_book.extension.activity

@Composable
fun startActivity(intent: Intent): () -> Unit {
	val activity = activity<Activity>()
	return {
		activity.startActivity(intent)
	}
}