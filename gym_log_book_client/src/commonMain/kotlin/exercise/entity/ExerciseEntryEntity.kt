@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.entity

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
	"exercise_items",
	indices = [Index(value = ["date", "exerciseTypeId"])]
)
data class ExerciseEntryEntity(
	@PrimaryKey val id: Uuid,

	val date: Instant,

	var exerciseTypeId: Uuid,
	var setNumber: Int,
	var weight: Double,
	var reps: Int,
)
