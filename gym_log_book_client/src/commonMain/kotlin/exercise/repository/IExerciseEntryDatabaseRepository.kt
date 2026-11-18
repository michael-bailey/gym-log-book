@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseEntryDatabaseRepository : IExerciseEntryRepository {
	val allExerciseEntryIds: Flow<Collection<Uuid>>

	fun updateEntity(entity: ExerciseEntryEntity): Deferred<Result<Unit>>
	fun deleteEntites(ids: List<Uuid>): Deferred<List<Throwable>>


}