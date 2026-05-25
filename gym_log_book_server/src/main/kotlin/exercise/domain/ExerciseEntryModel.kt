@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.domain

import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ExerciseEntryModel(
	val id: Uuid,
	val exerciseTypeId: Uuid,
	val creationInstant: Instant,
	val setNumber: Int,
	val weight: Double,
	val reps: Int,
)
