@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ILocalExerciseTypeRepository : IExerciseTypeRepository {
	val allLocalExerciseTypes: Flow<Collection<ExerciseTypeEntity>>
	val allLocalExerciseTypesMap: Flow<Map<Uuid, ExerciseTypeEntity>>
}