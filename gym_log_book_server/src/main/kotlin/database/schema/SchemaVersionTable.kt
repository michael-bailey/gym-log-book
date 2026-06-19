package net.michael_bailey.gym_log_book.server.database.schema

import org.jetbrains.exposed.v1.core.dao.id.UuidTable
import org.koin.core.annotation.Single
import kotlin.time.Clock

@Single
class SchemaVersionTable(
	private val clock: Clock
) : UuidTable("schema_version") {
	val version = double("version")
	val appliedAt = long("applied_at").default(clock.now().toEpochMilliseconds())
	val description = varchar("description", 255)
}