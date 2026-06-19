package net.michael_bailey.gym_log_book.server.database.schema

import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.JdbcTransaction
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils
import org.koin.core.annotation.Single

@Single
class DatabaseMigrationRunner(
	private val database: Database,
	private val schemaTable: SchemaVersionTable,
	private val schemas: List<DatabaseSchema>,
) {

	fun initialise() = transaction(database) {
		runSchemaTableMigration()
		val current = getCurrentVersion()
		schemas
			.filter { it.version > current }
			.sortedBy { it.version }
			.forEach(migrateTable(this))
		commit()
	}

	private fun migrateTable(transaction: JdbcTransaction): (DatabaseSchema) -> Unit = { schema ->
		with(schema) { transaction.migrateTo() }
		schemaTable.insert {
			it[schemaTable.version] = schema.version
			it[schemaTable.description] = schema.description
		}
	}

	private fun JdbcTransaction.runSchemaTableMigration() {
		MigrationUtils.statementsRequiredForDatabaseMigration(schemaTable)
			.forEach {
				exec(it)
			}
		commit()
	}

	private fun getCurrentVersion(): Double = schemaTable
		.selectAll()
		.orderBy(schemaTable.version, SortOrder.DESC)
		.limit(1)
		.lastOrNull()
		?.get(schemaTable.version) ?: 0.0
}