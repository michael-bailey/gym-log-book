package io.github.michael_bailey.gym_log_book.lib.table

import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.lib.Loggable

abstract class BaseTable<T> : Loggable(), ITable<T> {
	var store: MutableList<T> = mutableListOf()

	val listState = MutableLiveData(store)


	override fun getRowCount(): Int = store.count()
	override fun getRow(index: Int): T = store[index]

	override fun appendRow(item: T) {
		log("appending item")
		store.add(item)
		listState.postValue(store)
		adapter.notifyDataSetChanged()
		save()
	}
}