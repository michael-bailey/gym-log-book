package org.british_information_technologies.gym_log_book.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.british_information_technologies.gym_library.data_type.EquipmentClass
import org.british_information_technologies.gym_log_book.database.AppDatabase

/**
 * Migrates from [AppDatabase] version 2 to three
 *
 * Changes here are:
 * - Addition of `equipment` column, which uses [EquipmentClass] as a mapping
 * - Translated `usesUserWeight` to the equivalent `equipment` column value
 *
 * This is my first migration, that is not automatic.
 * THis is a bit terrifying, but as i'm not removing data, should be good
 */
object MigrationTwoToThree : Migration(2, 3) {
	override fun migrate(db: SupportSQLiteDatabase) {

		db.beginTransaction()

		val res = runCatching {

			// adds the new column
			db.execSQL(
				"""
					ALTER TABLE exercise_types
					ADD COLUMN equipment TEXT NOT NULL DEFAULT 'none'
				""".trimIndent()
			)

			// translates the values over to the new column.
			db.execSQL(
				"""
					UPDATE exercise_types
					SET equipment = CASE
						WHEN usesUserWeight = 0 THEN "machine"
						ELSE "user_weight_machine"
					end;
				""".trimIndent()
			)
		}

		if (res.isSuccess) {
			// mark transaction as successful
			db.setTransactionSuccessful()
		}

		// clean up, or commit
		db.endTransaction()
	}
}