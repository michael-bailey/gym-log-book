package org.british_information_technologies.gym_log_book.database.view

import androidx.room.ColumnInfo
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class EntExerciseIdTimeView(
	@ColumnInfo(name = "id")
	val id: UUID,

	@ColumnInfo(name = "createdDate")
	val createdDate: LocalDate = LocalDate.now(),

	@ColumnInfo(name = "createdTime")
	val createdTime: LocalTime = LocalTime.now(),
)
