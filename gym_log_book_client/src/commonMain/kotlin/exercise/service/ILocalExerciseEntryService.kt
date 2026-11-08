@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ILocalExerciseEntryService : IExerciseEntryService {
	val allLocalEntryIds: Flow<Collection<Uuid>>
}