package org.british_information_technologies.gym_log_book.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object OneToTwoMigration : Migration(1, 2) {
	override fun migrate(database: SupportSQLiteDatabase) {
		// create edge table
		database.execSQL(
			"""
			CREATE TABLE `group_to_type_edge` IF NOT EXISTS
			(
				`id` INTEGER,
				`name` TEXT,
				PRIMARY KEY(`id`)
			)
		""".trimIndent()
		)

		database.execSQL(
			"""
			CREATE TABLE `exercise_groups` IF NOT EXISTS
			(
				`id` INTEGER,
				`name` TEXT,
				PRIMARY KEY(`id`)
			)
		""".trimIndent()
		)

		database.execSQL(
			"""
			ALTER TABLE exercise_items
			ADD COLUMN exerciseTypeId INTEGER
		""".trimIndent()
		)
	}
}