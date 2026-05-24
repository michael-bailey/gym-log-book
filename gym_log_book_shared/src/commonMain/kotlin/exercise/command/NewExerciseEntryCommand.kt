@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.shared.exercise.command

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
class NewExerciseEntryCommand(
	val typeId: Uuid,
	val creationTime: LocalDateTime,
	val setNumber: Int,
	val weight: Double,
	val reps: Int
)