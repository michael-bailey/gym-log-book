package io.github.michael_bailey.gym_log_book.lib.table

import java.io.Serializable

/// An Interface that describes a table
interface ITable<T> {
	val tableName: String

	fun getRowCount(): Int
	fun getRow(index: Int): T

	fun appendRow(item: T)

	fun save()
	fun load()
}

inline fun <reified T : Serializable> ITable<T>.getColumns() {
	val fields = T::class.java.declaredFields
	return fields.forEach {
		it.name
	}
}