package net.michael_bailey.gym_log_book.server.exercise.factory

import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel

interface IExerciseTypeFactory {
	fun create(newExerciseType: NewExerciseTypeModel): ExerciseTypeModel
}
