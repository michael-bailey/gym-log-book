package org.british_information_technologies.gym_log_book.database.entity

import androidx.room.Entity
import androidx.room.Index
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period

@Entity(
	"gym_session_entry",
	indices = [Index(value = ["date"])]
)
data class EntGymSessionEntry(
	val date: LocalDate,
	val startTime: LocalTime,
	val period: Period,
	val location: String?
)
