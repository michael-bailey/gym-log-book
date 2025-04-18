package org.british_information_technologies.gym_log_book.lib.navigation

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.british_information_technologies.gym_log_book.R
import org.british_information_technologies.gym_log_book.extensions.activity

@Composable
fun navigate(
	destination: String
): State<() -> Unit> {
	val activity = activity<Activity>()
	val nav = NavLocal.current
	val functionState = remember {
		mutableStateOf(
			{
				Toast(activity).apply {
					duration = Toast.LENGTH_LONG
					this.setText(R.string.navigation_graph_missing)
				}.show()
			}
		)
	}

	LaunchedEffect(key1 = nav, key2 = destination) {
		functionState.value = {
			nav!!.navigate(destination)
		}
	}
	return functionState
}