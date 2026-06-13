import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.JdbcTransaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils

abstract class RepositoryUnitTest : DatabaseUnitTest() {

	abstract val tables: Array<Table>

	override fun withDatabase(block: JdbcTransaction.() -> Unit) {
		super.withDatabase {
			val statements = MigrationUtils.statementsRequiredForDatabaseMigration(*tables)
			statements.forEach { exec(it) }

			block()
		}
	}

	fun withTestDatabase(block: suspend JdbcTransaction.(TestScope) -> Unit) = withDatabase {
		runTest {
			block(this)
		}
	}
}