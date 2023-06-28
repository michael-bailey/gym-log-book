package io.github.michael_bailey.gym_log_book.lib.componenets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.michael_bailey.gym_log_book.lib.Validator

enum class FieldType {
	String,
	Number,
	Float
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidatorTextField(
	state: String,
	validator: Validator,
	placeholder: String,
	onChange: (String) -> Unit,
) {

	val errorState = validator.validator(state).let {
		if (it.isSuccess) {
			state to ""
		} else {
			state to it.exceptionOrNull()!!.message!!
		}
	}


	Column {
		OutlinedTextField(
			value = errorState.first,
			onValueChange = onChange,
			label = { Text(placeholder) },
			singleLine = true,
			isError = errorState.second != "",
			keyboardOptions = validator.keyboardOptions
		)
		Text(errorState.second, color = Color.Red)
	}
}