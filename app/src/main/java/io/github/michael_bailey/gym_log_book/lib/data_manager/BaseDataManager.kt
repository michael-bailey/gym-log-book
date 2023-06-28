package io.github.michael_bailey.gym_log_book.lib.data_manager

import androidx.lifecycle.MediatorLiveData
import io.github.michael_bailey.gym_log_book.lib.table.ITable

/**
 * base data manager that prevents direct data manipulation.
 *
 * This is because i managed to erase my actual data from my device :(
 */
abstract class BaseDataManager<T>(
	protected val table: ITable<T>
) {

	val liveData: MediatorLiveData<List<T>> = MediatorLiveData<List<T>>()
	val rawData = table.getRows().toList()

	init {
		liveData.apply {
			addSource(table.liveData) {
				this.value = it
			}
			value = table.liveData.value
		}
	}

	/**
	 * Appends a new item and notifies observers
	 */
	fun append(
		factory: (Int) -> T
	) {
		table.addRow(factory(table.getRowCount()))
	}

	fun update(
		id: Int,
		updater: T.() -> Unit
	) {
		table.updateRow(id, updater)
	}

	fun delete(id: Int) {
		table.removeRow(id)
	}
}
