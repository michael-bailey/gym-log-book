package io.github.michael_bailey.gym_log_book.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(
	"exercise_items",
	indices = [Index(value = ["createdDate", "createdTime", "exercise"])]
)
data class EntExerciseEntry(
	@PrimaryKey() val id: UUID = UUID.randomUUID(),

	val createdDate: LocalDate,
	val createdTime: LocalTime,

	var exercise: String,
	var setNumber: Int,
	var weight: Double,
	var reps: Int,
)
