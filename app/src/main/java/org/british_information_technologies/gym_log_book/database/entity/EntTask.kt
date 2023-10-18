package org.british_information_technologies.gym_log_book.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(
	"tasks",
	indices = [Index(value = ["createdDate", "createdTime", "isComplete"])]
)
data class EntTask(
	@PrimaryKey() val id: UUID = UUID.randomUUID(),

	val createdDate: LocalDate = LocalDate.now(),
	val createdTime: LocalTime = LocalTime.now(),

	val text: String,
	val isComplete: Boolean
)