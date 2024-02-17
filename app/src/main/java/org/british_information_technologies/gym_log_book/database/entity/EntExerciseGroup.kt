package org.british_information_technologies.gym_log_book.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(
	"exercise_groups",
	indices = [Index(value = ["createdDate", "createdTime"])]
)
data class EntExerciseGroup(
	@PrimaryKey val id: UUID = UUID.randomUUID(),

	val createdDate: LocalDate = LocalDate.now(),
	val createdTime: LocalTime = LocalTime.now(),

	val name: String
)
