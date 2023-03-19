package io.github.michael_bailey.gym_log_book.lib.data_manager

import androidx.recyclerview.widget.RecyclerView
import io.github.michael_bailey.gym_log_book.lib.table.ITable

interface IDataManager<T> {
	val adapter: RecyclerView.Adapter<*>
	val table: ITable<T>


	fun append(
		constuct: (IBuilder<T>) -> IBuilder<T>
	)

	fun update(
		id: Long,
		updater: (T) -> T
	)

	fun deleteLast()
}