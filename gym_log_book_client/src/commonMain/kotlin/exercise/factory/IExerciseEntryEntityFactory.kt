@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.factory

import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseEntryEntityFactory {
	fun create(exerciseTypeId: Uuid, setNumber: Int, weight: Double, reps: Int): ExerciseEntryEntity
}