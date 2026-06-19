package net.michael_bailey.gym_log_book.server.database

import net.michael_bailey.gym_log_book.server.database.schema.SchemaVersionTable
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.TransactionManager
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import java.sql.Connection
import kotlin.time.Clock


@Module
@ComponentScan("net.michael_bailey.gym_log_book.server.database")
@Configuration
class DatabaseModule {

	@Single
	fun schemaTable(clock: Clock): SchemaVersionTable = SchemaVersionTable(clock)

	@Single
	fun database(): Database = Database.connect(
		System.getenv("DATABASE_URL")?.takeUnless { it.isBlank() } ?: "jdbc:sqlite:./database.sqlite",
		"org.sqlite.JDBC"
	)
		.also { TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE }
}
