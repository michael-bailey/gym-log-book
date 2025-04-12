package org.british_information_technologies.gym_library.database.converter

import androidx.room.TypeConverter
import java.time.LocalTime


class TimeConverter {
	@TypeConverter
	fun localTimeToLong(time: LocalTime): Long = time.toNanoOfDay()

	@TypeConverter
	fun longToLocalTime(long: Long): LocalTime = LocalTime.ofNanoOfDay(long)
}