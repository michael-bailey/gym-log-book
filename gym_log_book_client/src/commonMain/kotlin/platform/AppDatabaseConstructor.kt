package net.michael_bailey.gym_log_book.client.platform

import androidx.room3.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
	override fun initialize(): AppDatabase
}