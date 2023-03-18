package io.github.michael_bailey.gym_log_book.lib.table

import androidx.recyclerview.widget.RecyclerView

abstract class AdapterTable<T> : BaseTable<T>() {
	abstract val adapter: RecyclerView.Adapter<*>

	override fun appendRow(item: T) {
		super.appendRow(item)
		adapter.notifyDataSetChanged()
	}
}