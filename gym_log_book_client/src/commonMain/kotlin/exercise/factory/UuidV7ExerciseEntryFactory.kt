@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.factory

import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UuidV7ExerciseEntryFactory(
	private val clock: Clock
) : IExerciseEntryEntityFactory {
	override fun create(
		exerciseTypeId: Uuid,
		setNumber: Int,
		weight: Double,
		reps: Int,
	): ExerciseEntryEntity = ExerciseEntryEntity(
		id = Uuid.generateV7(),
		date = clock.now(),
		exerciseTypeId = exerciseTypeId,
		setNumber = setNumber,
		weight = weight,
		reps = reps,
		oldId = null
	)
}