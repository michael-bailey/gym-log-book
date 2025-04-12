package org.british_information_technologies.gym_log_book.table

import android.content.Context
import io.github.michael_bailey.android_chat_kit.extension.any.log
import org.british_information_technologies.gym_library.data_type.WeightItem
import org.british_information_technologies.gym_log_book.lib.table.CSVTable
import java.time.LocalDate

class WeightTable(val context: Context) : CSVTable<WeightItem>(context) {

	override val tableName: String
		get() = "Weight"

	override fun formatHeader(): String {
		log("called format header")
		return "'ID', 'Date', 'Weight'"
	}

	override fun decodeEntry(entry: String): WeightItem = entry.let {
		log("decoding: $it ")
		val (id, date, Weight) = it.split(", ", ignoreCase = false, limit = 3)
		WeightItem(id.toInt(), LocalDate.parse(date), Weight.toDouble())
	}

	override fun formatEntry(entry: WeightItem): String {
		log("Formatting entry")
		return "${entry.id}, ${entry.date}, ${entry.weight}"
	}
}