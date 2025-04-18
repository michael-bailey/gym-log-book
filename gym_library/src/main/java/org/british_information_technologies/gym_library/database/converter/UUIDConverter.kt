package org.british_information_technologies.gym_library.database.converter

import androidx.room.TypeConverter
import java.util.UUID


class UUIDConverter {
	@TypeConverter
	fun localTimeToLong(uuid: UUID): String = uuid.toString()

	@TypeConverter
	fun longToLocalTime(string: String): UUID = UUID.fromString(string)
}