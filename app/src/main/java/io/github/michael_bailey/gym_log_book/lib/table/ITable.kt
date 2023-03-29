package io.github.michael_bailey.gym_log_book.lib.table

import androidx.recyclerview.widget.RecyclerView

/// An Interface that describes a table
interface ITable<T> {
	val tableName: String
	val adapter: RecyclerView.Adapter<*>

	fun getRowCount(): Int

	fun getRows(): List<T>
	fun getRow(index: Int): T

	fun appendRow(item: T)

	fun removeRow()

	fun save()
	fun load()
}
