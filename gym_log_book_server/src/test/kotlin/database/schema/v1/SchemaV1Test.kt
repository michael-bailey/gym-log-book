package database.schema.v1

import DatabaseUnitTest
import net.michael_bailey.gym_log_book.server.database.schema.v1.ExerciseEntryTable
import net.michael_bailey.gym_log_book.server.database.schema.v1.ExerciseTypeTable
import net.michael_bailey.gym_log_book.server.database.schema.v1.SchemaV1
import org.jetbrains.exposed.v1.jdbc.selectAll
import kotlin.test.Test

class SchemaV1Test : DatabaseUnitTest() {
	@Test
	fun `test version 1 schema is commited to database`() = withDatabase {

		val underTest = SchemaV1

		println("Migration statements: $statements")

		with(underTest) { migrateTo() }

		assert(ExerciseTypeTable.selectAll().count() == 0L)
		assert(ExerciseEntryTable.selectAll().count() == 0L)
	}
}