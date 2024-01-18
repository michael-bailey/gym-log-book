package org.british_information_technologies.gym_log_book.lib.form.input_types

class IntegerField(
	onChange: (Int) -> Unit
) : TextInput<Int>(onChange)
