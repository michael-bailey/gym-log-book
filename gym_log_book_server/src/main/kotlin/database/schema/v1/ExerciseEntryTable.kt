@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.database.schema.v1

import org.jetbrains.exposed.v1.core.dao.id.UuidTable
import org.jetbrains.exposed.v1.datetime.timestamp
import kotlin.uuid.ExperimentalUuidApi

object ExerciseEntryTable : UuidTable(name = "exercise_entry") {
	val exerciseTypeId = this.uuid("exercise_type_id").references(ExerciseTypeTable.id)
	val createTime = this.timestamp("created_time")
	val setNumber = this.integer("set_number")
	val weight = this.double("weight")
	val reps = this.integer("reps")
}