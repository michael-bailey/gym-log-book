package io.github.michael_bailey.gym_log_book.lib.exceptions

class EmptyInputException : Exception("Cannot leave empty")
class ItemExistsException(id: Int) : Exception("item $id already exists")
class NegativeNumberException : Exception("Cannot use negative numbers")
class MultipleDecimalPointException : Exception("Too many decimals")
class DecimalPointException : Exception("Decimal not accepted")