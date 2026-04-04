@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.shared.exercise.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.annotations.Rpc
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Rpc
interface ExerciseEntryController {

	fun getExerciseEntries(): Flow<Collection<ExerciseEntry>>

	suspend fun createEntry(exerciseTypeId: Uuid, entrySetNumber: Int, entryWeight: Double, entryReps: Int): ExerciseEntry
}