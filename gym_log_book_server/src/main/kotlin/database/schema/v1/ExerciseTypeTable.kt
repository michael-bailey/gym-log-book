package net.michael_bailey.gym_log_book.server.database.schema.v1

import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object ExerciseTypeTable : UuidTable(name = "exercise_type") {
	val name = varchar("name", length = 32)
	val equipmentClass = this.varchar("equipment_class", length = 16)
	val equipmentClassText = this.varchar("equipment_class_text", length = 16).nullable()
}