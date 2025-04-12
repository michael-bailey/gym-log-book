package org.british_information_technologies.gym_log_book.lib.table

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.android_chat_kit.extension.any.log
import org.british_information_technologies.gym_log_book.lib.Identifiable

abstract class BaseTable<T : Identifiable> : ITable<T> {
	val _liveData: MutableLiveData<List<T>> = MutableLiveData(listOf())
	override val liveData: LiveData<List<T>> get() = _liveData

	override fun getRowCount(): Int = _liveData.value!!.count()
	override fun getRows(): List<T> = liveData.value!!.toList()
	override fun getRow(index: Int): T = liveData.value!![index]

	override fun addRow(item: T) {
		log("adding row")
		_liveData.value = (getRows() + item)
		save()
	}

	override fun updateRow(id: Int, update: T.() -> Unit) {
		log("updating row")
		_liveData.value = (getRows().map {
			if (it.id == id) {
				it.update()
			}
			it
		})
		save()
	}

	override fun removeRow(id: Int) {
		log("removing row")
		_liveData.value = (getRows().filter { it.id != id })
		save()
	}
}