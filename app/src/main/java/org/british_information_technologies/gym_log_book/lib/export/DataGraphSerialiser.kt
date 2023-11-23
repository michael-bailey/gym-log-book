package org.british_information_technologies.gym_log_book.lib.export

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseEntry
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import org.british_information_technologies.gym_log_book.database.entity.EntWeightEntry
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID


object DataGraphSerialiser : JsonSerializer<DataGraph>,
	JsonDeserializer<DataGraph> {
	override fun serialize(
		src: DataGraph?,
		typeOfSrc: Type?,
		context: JsonSerializationContext?
	): JsonElement = JsonObject().apply {

		// adds exercises
		add("exercise_entries", JsonArray().apply {
			src?.exerciseEntries?.forEach {
				add(JsonObject().apply {
					addProperty("id", it.id.toString())

					addProperty("created_date", it.createdDate.toString())
					addProperty("created_time", it.createdTime.toString())

					addProperty("exercise_type_id", it.exerciseTypeId.toString())

					addProperty("set_number", it.setNumber.toString())
					addProperty("weight", it.weight.toString())
					addProperty("reps", it.reps.toString())
				})
			}
		})

		// adds types
		add("exercise_types", JsonArray().apply {
			src?.exerciseTypes?.forEach {
				add(JsonObject().apply {
					addProperty("id", it.id.toString())

					addProperty("create_date", it.createdDate.toEpochDay())
					addProperty("created_time", it.createdTime.toNanoOfDay())

					addProperty("name", it.name)
					addProperty("uses_user_weight", it.usesUserWeight.toString())
				})
			}
		})

		// adds weight
		add("weight_entries", JsonArray().apply {
			src?.weightEntries?.forEach {
				add(JsonObject().apply {
					addProperty("id", it.id.toString())

					addProperty("create_date", it.createdDate.toString())
					addProperty("created_time", it.createdTime.toString())

					addProperty("value", it.value)
				})
			}
		})
	}

	override fun deserialize(
		json: JsonElement?,
		typeOfT: Type?,
		context: JsonDeserializationContext?
	): DataGraph? =
		json?.asJsonObject?.let { root ->
			DataGraph(
				exerciseEntries = root.get("exercise_entries")?.asJsonArray?.map { arr ->
					arr.asJsonObject.let { it ->
						EntExerciseEntry(
							createdDate = it["created_date"].asLong.let {
								LocalDate.ofEpochDay(
									it
								)
							},
							createdTime = it["created_time"].asLong.let {
								LocalTime.ofNanoOfDay(
									it
								)
							},
							exerciseTypeId = it["exercise_type_id"].let { UUID.fromString(it.asString) },
							setNumber = it["set_number"].asNumber as Int,
							weight = it["weight"].asNumber as Double,
							reps = it["reps"].asNumber as Int,
						)
					}
				} ?: listOf(),
				exerciseTypes = root.get("exercise_entries")?.asJsonArray?.map { arr ->
					arr.asJsonObject.let { it ->
						EntExerciseType(
							createdDate = it["created_date"].asLong.let {
								LocalDate.ofEpochDay(
									it
								)
							},
							createdTime = it["created_time"].asLong.let {
								LocalTime.ofNanoOfDay(
									it
								)
							},
							name = it["name"].asString,
							usesUserWeight = it["uses_user_weight"].asBoolean,
						)
					}
				} ?: listOf(),
				weightEntries = root.get("weight_entries")?.asJsonArray?.map { arr ->
					arr.asJsonObject.let { it ->
						EntWeightEntry(
							createdDate = it["created_date"].asLong.let {
								LocalDate.ofEpochDay(
									it
								)
							},
							createdTime = it["created_time"].asLong.let {
								LocalTime.ofNanoOfDay(
									it
								)
							},
							value = it["value"].asDouble,
						)
					}
				} ?: listOf()
			)
		}
}