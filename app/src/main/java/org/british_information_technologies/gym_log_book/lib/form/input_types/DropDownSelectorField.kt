package org.british_information_technologies.gym_log_book.lib.form.input_types

import androidx.compose.runtime.State

data class DropDownSelectorField<S>(
	val state: State<S>,
	val mapping: Map<S, String>
) : TextInput()
