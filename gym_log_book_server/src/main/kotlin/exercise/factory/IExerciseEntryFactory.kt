package net.michael_bailey.gym_log_book.server.exercise.factory

import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel

interface IExerciseEntryFactory {
	fun createEntry(newExerciseEntry: NewExerciseEntryModel): ExerciseEntryModel
}
