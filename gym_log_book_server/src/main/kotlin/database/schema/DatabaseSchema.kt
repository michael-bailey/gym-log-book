package net.michael_bailey.gym_log_book.server.database.schema

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.JdbcTransaction

interface DatabaseSchema {
	val version: Double
	val description: String
	val tables: Set<Table>

	fun JdbcTransaction.migrateTo()
	fun JdbcTransaction.migrateback() {}
}
