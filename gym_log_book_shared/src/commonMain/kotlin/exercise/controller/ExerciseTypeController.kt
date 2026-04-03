package net.michael_bailey.gym_log_book.shared.exercise.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.annotations.Rpc
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType

@Rpc
interface ExerciseTypeController {

	fun exerciseTypes(): Flow<Collection<ExerciseType>>

	suspend fun createExerciseType(
		name: String
	): ExerciseType

}