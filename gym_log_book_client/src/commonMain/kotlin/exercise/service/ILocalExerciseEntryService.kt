@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ILocalExerciseEntryService : IExerciseEntryService {

	val allLocalEntryIds: Flow<Collection<Uuid>>

	suspend fun getLocalEntry(id: Uuid): ExerciseEntryEntity

	suspend fun createNewLocalEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int,
	)

	suspend fun updateLocalEntry()
	suspend fun deleteLocalEntry(id: Uuid)

	suspend fun deleteLocalEntries(ids: List<Uuid>)

}