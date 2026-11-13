@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface INetworkExerciseTypeService : IExerciseTypeService {
	val networkExerciseTypes: Flow<Collection<ExerciseType>>
	val networkExerciseNamesMap: Flow<Map<Uuid, String>>
}