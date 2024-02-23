package org.british_information_technologies.gym_log_book.activity.add_weight_activity

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.michael_bailey.android_chat_kit.extension.any.log
import org.british_information_technologies.gym_log_book.extension.activity
import org.british_information_technologies.gym_log_book.lib.componenets.ValidatorTextField
import org.british_information_technologies.gym_log_book.lib.validation.Validator
import org.british_information_technologies.gym_log_book.theme.Title

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
			Surface(modifier = Modifier.padding(it)) {
				Column(
					Modifier.fillMaxSize(),
					verticalArrangement = Arrangement.SpaceEvenly,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Text("Hi there", fontSize = Title)
					Column(Modifier.width(IntrinsicSize.Min)) {
						ValidatorTextField(
							state = currentWeight,
							validator = Validator.FloatValidator(isLast = true),
							placeholder = "Weight...",
							onChange = { log("replace this") }
						)
					}
					Row(
						Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.SpaceEvenly,
						verticalAlignment = Alignment.CenterVertically
					) {
						Button(onClick = { activity.finish() }) {
							Text(text = "Cancel")
						}
						Button(
							onClick = { log("replace this") },
							enabled = isSubmitEnabled
						) {
							Text(text = "Submit")
						}
					}
				}
			}
		}
	)
}