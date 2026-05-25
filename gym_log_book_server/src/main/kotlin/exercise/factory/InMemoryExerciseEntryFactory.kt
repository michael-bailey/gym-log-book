@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.factory

import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory
class InMemoryExerciseEntryFactory : IExerciseEntryFactory {
	override fun createEntry(newExerciseEntry: NewExerciseEntryModel): ExerciseEntryModel = ExerciseEntryModel(
		id = Uuid.random(),
		exerciseTypeId = newExerciseEntry.exerciseTypeId,
		creationInstant = newExerciseEntry.creationInstant,
		setNumber = newExerciseEntry.setNumber,
		weight = newExerciseEntry.weight,
		reps = newExerciseEntry.reps,
	)
}
