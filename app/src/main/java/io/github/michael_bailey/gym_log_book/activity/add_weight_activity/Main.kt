package io.github.michael_bailey.gym_log_book.activity.add_weight_activity

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.github.michael_bailey.gym_log_book.lib.componenets.ValidatorTextField
import io.github.michael_bailey.gym_log_book.lib.validation.Validator
import io.github.michael_bailey.gym_log_book.theme.Title


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: AddWeightViewModel) {

	val activity = LocalContext.current as Activity

	val currentWeight by vm.weight.observeAsState(initial = "")
	val isSubmitEnabled by vm.isSubmitEnabled.observeAsState(initial = false)

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
							onChange = { vm.setWeight(it) }
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
							onClick = { vm.finalise(); activity.finish() },
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