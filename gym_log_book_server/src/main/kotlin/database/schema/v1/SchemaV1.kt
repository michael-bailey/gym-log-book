package net.michael_bailey.gym_log_book.server.database.schema.v1

import net.michael_bailey.gym_log_book.server.database.schema.DatabaseSchema
import org.jetbrains.exposed.v1.core.dao.id.UuidTable
import org.jetbrains.exposed.v1.jdbc.JdbcTransaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils
import org.koin.core.annotation.Single

@Single
object SchemaV1 : DatabaseSchema {
	override val version: Double
		get() = 1.0
	override val description: String
		get() = "Initial type, entry, and schema versioning setup"
	override val tables: Set<UuidTable>
		get() = setOf(
			ExerciseTypeTable,
			ExerciseEntryTable,
		)

	override fun JdbcTransaction.migrateTo() {
		MigrationUtils.statementsRequiredForDatabaseMigration(*tables.toTypedArray())
			.forEach { exec(it) }
	}
}