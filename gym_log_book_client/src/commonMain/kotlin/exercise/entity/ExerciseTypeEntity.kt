@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
	"exercise_types",
	indices = [Index(value = ["date", "name"])]
)
data class ExerciseTypeEntity(
	@PrimaryKey() val id: Uuid,

	val date: Instant,

	val name: String,
	val usesUserWeight: Boolean,

	@ColumnInfo(name = "equipment", defaultValue = "none")
	val equipmentClass: EquipmentClass,

	)
