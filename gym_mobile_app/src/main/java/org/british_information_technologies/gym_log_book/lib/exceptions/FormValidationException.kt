package org.british_information_technologies.gym_log_book.lib.exceptions

sealed class FormValidationException(message: String) : Exception(message) {
	class EmptyInputException : FormValidationException("Cannot leave empty")
	class ItemExistsException(id: Int) :
		FormValidationException("item $id already exists")

	class NegativeNumberException :
		FormValidationException("Cannot use negative numbers")

	class MultipleDecimalPointException :
		FormValidationException("Too many decimals")

	class DecimalPointException : FormValidationException("Decimal not accepted")
}

