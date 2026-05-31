package net.michael_bailey.gym_log_book.server.database

import io.ktor.server.application.*
import net.michael_bailey.gym_log_book.server.database.schema.DatabaseMigrationRunner
import org.koin.ktor.plugin.koin

fun Application.setupDatabase() {
	val migrationRunner: DatabaseMigrationRunner = koin().get()
	migrationRunner.initialise()
}