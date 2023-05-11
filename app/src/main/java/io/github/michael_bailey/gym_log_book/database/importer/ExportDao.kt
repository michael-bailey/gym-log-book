package io.github.michael_bailey.gym_log_book.database.importer

interface ExportDao<T> {
	fun insert(ent: T)
}