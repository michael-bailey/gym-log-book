package io.github.michael_bailey.gym_log_book.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.michael_bailey.gym_log_book.database.converter.DateConverter
import io.github.michael_bailey.gym_log_book.database.converter.TimeConverter
import io.github.michael_bailey.gym_log_book.database.converter.UUIDConverter
import io.github.michael_bailey.gym_log_book.database.dao.ExerciseEntryDao
import io.github.michael_bailey.gym_log_book.database.dao.ExerciseTypeDao
import io.github.michael_bailey.gym_log_book.database.dao.WeightEntryDao
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseEntry
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseType
import io.github.michael_bailey.gym_log_book.database.entity.EntWeightEntry

@Database(
	[
		EntExerciseEntry::class,
		EntExerciseType::class,
		EntWeightEntry::class,
	],
	version = 1,
	exportSchema = true,
)
@TypeConverters(
	DateConverter::class,
	TimeConverter::class,
	UUIDConverter::class
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun exerciseEntryDao(): ExerciseEntryDao
	abstract fun exerciseTypeDao(): ExerciseTypeDao
	abstract fun weightEntryDao(): WeightEntryDao
}