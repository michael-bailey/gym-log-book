package database

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.JdbcTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

abstract class DatabaseUnitTest {
	fun withDatabase(block: JdbcTransaction.() -> Unit) {
		val database: Database = Database.connect("jdbc:sqlite::memory:")
		transaction(database, statement = block)
	}
}