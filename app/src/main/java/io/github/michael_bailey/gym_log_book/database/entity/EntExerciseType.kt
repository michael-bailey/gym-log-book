package io.github.michael_bailey.gym_log_book.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
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
	val usesUserWeight: Boolean
)
