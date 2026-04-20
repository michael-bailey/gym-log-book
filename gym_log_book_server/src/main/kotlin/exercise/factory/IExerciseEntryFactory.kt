@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.factory

import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseEntryFactory {
	fun createEntry(exerciseTypeId: Uuid, entrySetNumber: Int, entryWeight: Double, entryReps: Int): ExerciseEntry

}