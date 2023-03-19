package io.github.michael_bailey.gym_log_book.lib.table

import android.content.Context
import io.github.michael_bailey.gym_log_book.lib.Identifiable
import java.io.FileNotFoundException

abstract class CSVTable<T : Identifiable>(
	private val ctx: Context
) : BaseTable<T>() {
	protected abstract fun formatHeader(): String
	protected abstract fun formatEntry(entry: T): String
	protected abstract fun decodeEntry(entry: String): T

	fun nextId(): Long = store.count().toLong()

	init {
		load()
	}

	override fun save() {
		log("Saving file")
		var out = ctx.openFileOutput("$tableName.table.csv", Context.MODE_PRIVATE)

		out.write("${formatHeader()}\n".toByteArray())
		out.use { o ->
			store.forEach {
				log("writing item")
				o.write("${formatEntry(it)}\n".toByteArray())
			}
		}
		out.flush()
	}

	override fun load() {
		try {
			val reader = ctx.openFileInput("$tableName.table.csv").bufferedReader()
			val nonEmpty = reader.lineSequence()
				.filter { it.isNotBlank() }
				.toList()
			store = nonEmpty.withIndex().map {
				if (it.index == 0) return@map null
				decodeEntry(it.value)
			}.filterNotNull().toMutableList()
			store.sortBy {
				it.id
			}
		} catch (e: FileNotFoundException) {
			log("file not found when loading")
			store = mutableListOf()
		}
	}
}