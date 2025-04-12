package org.british_information_technologies.gym_library.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate


class DateConverter {
	@TypeConverter
	fun localDateToLong(date: LocalDate): Long = date.toEpochDay()

	@TypeConverter
	fun longToLocalDate(long: Long): LocalDate = LocalDate.ofEpochDay(long)
}