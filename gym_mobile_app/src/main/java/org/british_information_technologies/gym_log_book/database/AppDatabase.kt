package org.british_information_technologies.gym_log_book.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.british_information_technologies.gym_log_book.database.dao.ExerciseEntryDao
import org.british_information_technologies.gym_log_book.database.dao.ExerciseGroupDao
import org.british_information_technologies.gym_log_book.database.dao.ExerciseTypeDao
import org.british_information_technologies.gym_log_book.database.dao.WeightEntryDao
import org.british_information_technologies.gym_log_book.database.edge.EdgeGroupToType
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseEntry
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseGroup
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import org.british_information_technologies.gym_log_book.database.entity.EntWeightEntry

@Database(
	entities = [
		EntExerciseEntry::class,
		EntExerciseType::class,
		EntWeightEntry::class,
		EntExerciseGroup::class,
		EdgeGroupToType::class,
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
	org.british_information_technologies.gym_library.database.converter.DateConverter::class,
	org.british_information_technologies.gym_library.database.converter.TimeConverter::class,
	org.british_information_technologies.gym_library.database.converter.UUIDConverter::class,
	org.british_information_technologies.gym_library.database.converter.EquipmentClassConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun exerciseEntryDao(): ExerciseEntryDao
	abstract fun exerciseTypeDao(): ExerciseTypeDao
	abstract fun weightEntryDao(): WeightEntryDao
	abstract fun exerciseGroupDao(): ExerciseGroupDao
}