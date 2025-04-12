package org.british_information_technologies.gym_log_book.lib.componenets.text_fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun OutlinedFloatField(
	modifier: Modifier = Modifier,
	value: String,
	onChanged: (String) -> Unit,
	placeholder: String,
) {
	OutlinedTextField(
		value = value,
		onValueChange = onChanged,
		modifier = Modifier.then(modifier),
		keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
		placeholder = { Text(text = placeholder) }
	)
}
