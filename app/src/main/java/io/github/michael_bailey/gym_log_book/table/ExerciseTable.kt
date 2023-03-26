package io.github.michael_bailey.gym_log_book.table

import android.content.Context
import androidx.compose.runtime.toMutableStateList
import androidx.recyclerview.widget.RecyclerView
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.lib.table.CSVTable
import io.github.michael_bailey.gym_log_book.main_activity.ExerciseRecyclerAdapter
import java.time.LocalDate

class ExerciseTable(val context: Context) : CSVTable<ExerciseItem>(context) {

	val listState = store.toMutableStateList()

	override val adapter: RecyclerView.Adapter<*> =
		ExerciseRecyclerAdapter(this) {
			log("adapter clicked")
		}

	override val tableName: String
		get() = "exercise"

	override fun formatHeader(): String =
		"'ID', 'Date', 'Exercise', 'SetNumber', 'Weight', 'Reps'"

	override fun decodeEntry(entry: String): ExerciseItem {
		val (id, date, exercise, setNumber, Weight, Reps) = entry.split(
			", ",
			ignoreCase = false,
			limit = 6
		)
		return ExerciseItem(
			id.toLong(),
			LocalDate.parse(date),
			exercise,
			setNumber.toInt(),
			Weight.toInt(),
			Reps.toInt()
		)
	}

	override fun formatEntry(entry: ExerciseItem): String {
		return entry.let { "${it.id}, ${it.date}, ${it.exercise}, ${it.setNumber}, ${it.weight}, ${it.reps}" }
	}
}

private operator fun <E : Any> List<E>.component6() = get(5)
