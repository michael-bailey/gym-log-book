package io.github.michael_bailey.gym_log_book.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(
	"weight_items",
	indices = [Index(value = ["createdDate", "createdTime"])]
)
data class EntWeightEntry(
	@PrimaryKey() val id: UUID = UUID.randomUUID(),

	val createdDate: LocalDate,
	val createdTime: LocalTime,

	val value: Double
)
