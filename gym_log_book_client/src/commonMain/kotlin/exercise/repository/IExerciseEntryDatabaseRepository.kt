@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseEntryDatabaseRepository : IExerciseEntryRepository {
	fun deleteEntites(ids: List<Uuid>): Deferred<List<Throwable>>

	val allExerciseEntryIds: Flow<Collection<Uuid>>
}