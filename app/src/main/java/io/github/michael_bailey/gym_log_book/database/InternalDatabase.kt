package io.github.michael_bailey.gym_log_book.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.michael_bailey.gym_log_book.database.converter.DateConverter
import io.github.michael_bailey.gym_log_book.database.converter.TimeConverter
import io.github.michael_bailey.gym_log_book.database.converter.UUIDConverter
import io.github.michael_bailey.gym_log_book.database.dao.TaskDao
import io.github.michael_bailey.gym_log_book.database.entity.EntTask

@Database(
	[
		EntTask::class
	],
	version = 1,
	exportSchema = true
)
@TypeConverters(
	DateConverter::class,
	TimeConverter::class,
	UUIDConverter::class
)
abstract class InternalDatabase : RoomDatabase() {
	abstract fun taskDao(): TaskDao
}