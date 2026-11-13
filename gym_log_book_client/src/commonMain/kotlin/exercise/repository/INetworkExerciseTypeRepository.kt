@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface INetworkExerciseTypeRepository : IExerciseTypeRepository {
	val allNetworkExerciseTypes: Flow<Collection<ExerciseType>>
	val allNetworkExerciseTypesMap: Flow<Map<Uuid, ExerciseType>>
}