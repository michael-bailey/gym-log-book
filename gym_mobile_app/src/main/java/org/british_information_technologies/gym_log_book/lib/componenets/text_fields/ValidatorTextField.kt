package org.british_information_technologies.gym_log_book.lib.componenets.text_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.british_information_technologies.gym_log_book.lib.validation.Validator

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
			keyboardOptions = validator.keyboard
		)
		Text(errorState.second, color = Color.Red)
	}
}