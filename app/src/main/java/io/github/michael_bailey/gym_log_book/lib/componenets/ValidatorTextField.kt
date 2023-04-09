package io.github.michael_bailey.gym_log_book.lib.componenets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import io.github.michael_bailey.gym_log_book.lib.Validator

enum class FieldType {
	String,
	Number,
	Float
}

@Composable
fun ValidatorTextField(
	state: State<String>,
	validator: Validator,
	placeholder: String,
	onChange: (String) -> Unit,
) {

	val errorState = remember {
		derivedStateOf {
			validator.validator(state.value).let {
				if (it.isSuccess) {
					state.value to ""
				} else {
					state.value to it.exceptionOrNull()!!.message!!
				}
			}
		}
	}

	Column {
		OutlinedTextField(
			value = errorState.value.first,
			onValueChange = onChange,
			label = { Text(placeholder) },
			singleLine = true,
			isError = errorState.value.second != "",
			keyboardOptions = validator.keyboardOptions
		)
		Text(errorState.value.second, color = Color.Red)
	}
}