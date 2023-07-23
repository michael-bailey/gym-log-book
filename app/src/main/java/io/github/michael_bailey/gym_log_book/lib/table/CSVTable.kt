package io.github.michael_bailey.gym_log_book.lib.table

import android.content.Context
import io.github.michael_bailey.android_chat_kit.extension.any.log
import io.github.michael_bailey.gym_log_book.lib.Identifiable
import java.io.FileNotFoundException

abstract class CSVTable<T : Identifiable>(
	private val ctx: Context
) : BaseTable<T>() {
	protected abstract fun formatHeader(): String
	protected abstract fun formatEntry(entry: T): String
	protected abstract fun decodeEntry(entry: String): T

	fun nextId(): Int = runCatching {
		liveData.value!!
			.sortedBy { it.id }.last().id + 1
	}.getOrNull() ?: 0

	init {
		load()
	}

	override fun save() {
		log("Saving file")
		liveData.value?.let {
			log("List isn't null")
			var out = ctx.openFileOutput("$tableName.table.csv", Context.MODE_PRIVATE)
			log("file exists")
			out.write("${formatHeader()}\n".toByteArray())
			log("wrote header")
			out.use { o ->
				it.forEach {
					o.write("${formatEntry(it)}\n".toByteArray())
				}
				log("wrote items")
			}
			out.flush()
			log("flushed")
		}
	}

	override fun load() {
		log("loading file")
		try {
			val reader = ctx.openFileInput("$tableName.table.csv").bufferedReader()
			log("opened reader")
			val nonEmpty = reader.lineSequence()
				.filter { it.isNotBlank() }
				.toList()
			log("filtered items")
			val tmpStore = nonEmpty.withIndex().map {
				if (it.index == 0) return@map null
				decodeEntry(it.value)
			}.filterNotNull().sortedBy { it.id }.toMutableList()
			log("sorted into mutable list")
			_liveData.postValue(tmpStore)
			log("assigned to stores")
		} catch (e: FileNotFoundException) {
			log("file not found when loading")
			_liveData.postValue(mutableListOf())
			log("assigned empty list to stores")
		}
	}
}