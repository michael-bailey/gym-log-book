package io.github.michael_bailey.gym_log_book.lib.table

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

/**
 * An Interface that describes a table
 */
interface ITable<T> {
	val tableName: String
	val adapter: RecyclerView.Adapter<*>
	val liveData: LiveData<List<T>>

	fun getRowCount(): Int

	fun getRows(): List<T>
	fun getRow(index: Int): T

	fun addRow(item: T)

	fun updateRow(id: Int, update: T.() -> Unit)

	fun removeRow(id: Int)

	fun save()
	fun load()
}
