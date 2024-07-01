package org.british_information_technologies.gym_log_book.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.british_information_technologies.gym_log_book.data_type.EquipmentClass
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(
	"exercise_types",
	indices = [Index(value = ["createdDate", "createdTime", "name"])]
)
data class EntExerciseType(
	@PrimaryKey() val id: UUID = UUID.randomUUID(),

	val createdDate: LocalDate = LocalDate.now(),
	val createdTime: LocalTime = LocalTime.now(),

	val name: String,
	val usesUserWeight: Boolean,

	@ColumnInfo(name = "equipment", defaultValue = "none")
	val equipmentClass: EquipmentClass,

)
