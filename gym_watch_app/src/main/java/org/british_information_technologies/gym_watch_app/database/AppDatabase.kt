package org.british_information_technologies.gym_watch_app.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.british_information_technologies.gym_library.database.converter.DateConverter
import org.british_information_technologies.gym_library.database.converter.EquipmentClassConverter
import org.british_information_technologies.gym_library.database.converter.TimeConverter
import org.british_information_technologies.gym_library.database.converter.UUIDConverter

@Database(
	entities = [
	],
	views = [
	],
	version = 3,
	exportSchema = true,
	autoMigrations = [
		AutoMigration(from = 1, to = 2)
	]
)
@TypeConverters(
	DateConverter::class,
	TimeConverter::class,
	UUIDConverter::class,
	EquipmentClassConverter::class,
)
abstract class AppDatabase : RoomDatabase()