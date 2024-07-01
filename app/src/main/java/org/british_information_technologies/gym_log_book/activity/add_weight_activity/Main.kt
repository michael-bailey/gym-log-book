package org.british_information_technologies.gym_log_book.activity.add_weight_activity

import android.app.Activity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.british_information_technologies.gym_log_book.extension.activity

@Deprecated("This is used for scrap", level = DeprecationLevel.ERROR)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {

	val activity = activity<Activity>()
	val currentWeight by remember { mutableStateOf("") }
	val isSubmitEnabled by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			TopAppBar(title = { Text("Add weight") })
		},
		content = {

		}
	)
}

