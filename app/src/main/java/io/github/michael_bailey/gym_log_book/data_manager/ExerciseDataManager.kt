package io.github.michael_bailey.gym_log_book.data_manager

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.lib.data_manager.IBuilder
import io.github.michael_bailey.gym_log_book.lib.data_manager.IDataManager
import io.github.michael_bailey.gym_log_book.table.ExerciseTable

class ExerciseDataManager(
	private val context: Context,
	override val table: ExerciseTable = ExerciseTable(context),
) : IDataManager<ExerciseItem> {

	override val adapter: RecyclerView.Adapter<*> get() = table.adapter

	override fun deleteLast() {
		TODO("Not yet implemented")
	}

	override fun update(id: Long, updater: (ExerciseItem) -> ExerciseItem) {
		TODO("Not yet implemented")
	}

	override fun append(constuct: (IBuilder<ExerciseItem>) -> IBuilder<ExerciseItem>) {
		TODO("Not yet implemented")
	}
}