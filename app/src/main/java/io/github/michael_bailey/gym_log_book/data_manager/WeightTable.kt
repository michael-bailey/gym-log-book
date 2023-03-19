package io.github.michael_bailey.gym_log_book.data_manager

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import io.github.michael_bailey.gym_log_book.data_type.WeightItem
import io.github.michael_bailey.gym_log_book.lib.table.CSVTable
import io.github.michael_bailey.gym_log_book.main_activity.WeightRecyclerAdapter
import java.time.LocalDate

class WeightTable(val context: Context) : CSVTable<WeightItem>(context) {

	override val tableName: String
		get() = "Weight"

	override val adapter: RecyclerView.Adapter<*>
		get() = WeightRecyclerAdapter(this) {
			log("adapter clicked")
		}

	override fun formatHeader(): String {
		log("called format header")
		return "'ID', 'Date', 'Weight'"
	}

	override fun decodeEntry(entry: String): WeightItem = entry.let {
		log("decoding: $it ")
		val (id, date, Weight) = it.split(", ", ignoreCase = false, limit = 3)
		WeightItem(id.toLong(), LocalDate.parse(date), Weight.toLong())
	}

	override fun formatEntry(entry: WeightItem): String {
		log("Formatting entry")
		return "${entry.id}, ${entry.date}, ${entry.weight}"
	}
}