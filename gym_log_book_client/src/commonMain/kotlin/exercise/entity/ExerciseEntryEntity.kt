@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.entity

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
	"exercise_items",
	indices = [Index(value = ["createdDate", "createdTime"])]
)
data class ExerciseEntryEntity(
	@PrimaryKey val id: Uuid = Uuid.generateV7(),

	val createdDate: LocalDate,
	val createdTime: LocalTime,

	var exerciseTypeId: Uuid,
	var setNumber: Int,
	var weight: Double,
	var reps: Int,
)
