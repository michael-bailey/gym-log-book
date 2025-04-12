package org.british_information_technologies.gym_log_book.lib.exceptions

class EmptyInputException : Exception("Cannot leave empty")
class UUIDInvalidException : Exception("UUID is not valid")
class ItemExistsException(id: Int) : Exception("item $id already exists")
class NegativeNumberException : Exception("Cannot use negative numbers")
class MultipleDecimalPointException : Exception("Too many decimals")
class DecimalPointException : Exception("Decimal not accepted")