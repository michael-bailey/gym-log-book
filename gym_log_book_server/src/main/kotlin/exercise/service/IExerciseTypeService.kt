package net.michael_bailey.gym_log_book.server.exercise.service

import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType

interface IExerciseTypeService {
	val exerciseTypes: Flow<Collection<ExerciseType>>
}