@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.shared.exercise.command

import kotlinx.serialization.Serializable
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class NewExerciseEntryCommand(
	val exerciseTypeId: Uuid,
	val creationInstant: Instant,
	val setNumber: Int,
	val weight: Double,
	val reps: Int
) {
	init {
		require(exerciseTypeId != Uuid.NIL) { "exerciseTypeId must not be nil." }
		require(setNumber > 0) { "setNumber must be greater than 0." }
		require(weight.isFinite()) { "weight must be finite." }
		require(weight >= 0.0) { "weight must be greater than or equal to 0." }
		require(reps > 0) { "reps must be greater than 0." }
	}
}
