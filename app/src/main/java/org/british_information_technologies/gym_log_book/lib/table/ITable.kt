package org.british_information_technologies.gym_log_book.lib.table

import androidx.lifecycle.LiveData

/**
 * An Interface that describes a table
 */
interface ITable<T> {
	val tableName: String
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
