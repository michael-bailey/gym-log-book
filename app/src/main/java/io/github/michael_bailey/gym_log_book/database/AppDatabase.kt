package io.github.michael_bailey.gym_log_book.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseEntry

@Database(
	[EntExerciseEntry::class],
	[],
	1,
	true
)
abstract class AppDatabase() : RoomDatabase() {
}