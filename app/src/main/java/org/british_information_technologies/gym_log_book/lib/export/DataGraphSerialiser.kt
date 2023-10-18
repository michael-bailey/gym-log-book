package org.british_information_technologies.gym_log_book.lib.export

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.DateFormat.getDateTimeInstance


object DataGraphSerialiser : JsonSerializer<DataGraph> {
	private val sdf = getDateTimeInstance()
	override fun serialize(
		src: DataGraph?,
		typeOfSrc: Type?,
		context: JsonSerializationContext?
	): JsonElement? {
		val obj = JsonObject()

		val exercises = JsonArray().apply {
			val obj = JsonObject()
//			src?.exerciseEntries.forEach {add()}
		}

//		obj.("lkj", src?.exerciseEntries)
		return null
	}
}