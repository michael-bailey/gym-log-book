package io.github.michael_bailey.gym_log_book.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

@Entity(
	"exercise_items",
	indices = [Index(value = ["createdDate", "createdTime"])]
)
data class EntExerciseEntry(
	@PrimaryKey val id: UUID = UUID.randomUUID(),

	val createdDate: LocalDate = LocalDate.now(),
	val createdTime: LocalTime = LocalTime.now(),

	var exerciseTypeId: UUID,
	var setNumber: Int,
	var weight: Double,
	var reps: Int,
) {
	fun getLocalDateTime(): LocalDateTime {
		return LocalDateTime.of(this.createdDate, this.createdTime)
	}
}
