package io.github.michael_bailey.gym_log_book.lib.data_manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.lib.table.ITable

/**
 * base data manager that prevents direct data manipulation.
 *
 * This is because i managed to erase my actual data from my device :(
 */
abstract class BaseDataManager<T>(
	protected val table: ITable<T>
) {

	private val mutableLiveData: MutableLiveData<List<T>> = MutableLiveData()
	val liveData: LiveData<List<T>> get() = mutableLiveData

	init {
		updateLiveData()
	}

	private fun updateLiveData() {
		val items = table.getRows()
		mutableLiveData.postValue(items)
	}

	/**
	 * Appends a new item and notifies observers
	 */
	fun append(
		factory: (Int) -> T
	) {
		table.appendRow(factory(table.getRowCount()))
		updateLiveData()
	}

	fun update(
		id: Int,
		updater: T.() -> T
	) {
		val item = table.getRow(id)
		item.updater()
		updateLiveData()
	}

	fun deleteLast() {
		table.removeRow()
		updateLiveData()
	}

	fun forceUpdate() {
		updateLiveData()
	}
}
