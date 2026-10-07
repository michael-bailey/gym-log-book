package net.michael_bailey.gym_log_book.client.platform

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase()

