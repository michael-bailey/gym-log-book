package database.schema

import database.DatabaseUnitTest
import io.mockk.*
import net.michael_bailey.gym_log_book.server.database.schema.DatabaseMigrationRunner
import net.michael_bailey.gym_log_book.server.database.schema.DatabaseSchema
import net.michael_bailey.gym_log_book.server.database.schema.SchemaVersionTable
import org.jetbrains.exposed.v1.jdbc.JdbcTransaction
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.junit.Test
import kotlin.time.Clock
import kotlin.time.Instant

class DatabaseMigrationRunnerTest : DatabaseUnitTest() {

	private val clock: Clock = mockk {
		every { now() } returns Instant.DISTANT_FUTURE
	}

	private val schema1: DatabaseSchema = mockk {
		every { version } returns 1.0
		every { description } returns "schema1"
		every { with(this@mockk) { any<JdbcTransaction>().migrateTo() } } just Runs
	}

	private val schema2: DatabaseSchema = mockk {
		every { version } returns 2.0
		every { description } returns "schema1"
		every { with(this@mockk) { any<JdbcTransaction>().migrateTo() } } just Runs
	}

	@Test
	fun `Initialise runs migrations for one schema`() = withDatabase {
		val schemaTable = SchemaVersionTable(clock = clock)

		val underTest = DatabaseMigrationRunner(
			database = db,
			schemaTable = schemaTable,
			schemas = listOf(schema1),
		)

		underTest.initialise()

		val version = getVersion(schemaTable)

		assert(version == 1.0)

		verify(exactly = 1) { with(schema1) { any<JdbcTransaction>().migrateTo() } }
		verify(exactly = 0) { with(schema2) { any<JdbcTransaction>().migrateTo() } }

	}

	private fun getVersion(schemaTable: SchemaVersionTable): Double {
		val row = schemaTable.selectAll().limit(10).last()
		val version = row[schemaTable.version]
		return version
	}

	@Test
	fun `Initialise runs migrations for two schemas, one after the other`() = withDatabase {
		val schemaTable = SchemaVersionTable(clock = clock)

		val underTest = DatabaseMigrationRunner(
			database = db,
			schemaTable = schemaTable,
			schemas = listOf(schema1, schema2),
		)

		underTest.initialise()

		val version = getVersion(schemaTable)

		assert(version == 2.0)

		verify(exactly = 1) {
			with(schema1) { any<JdbcTransaction>().migrateTo() }
			with(schema2) { any<JdbcTransaction>().migrateTo() }
		}
	}

}