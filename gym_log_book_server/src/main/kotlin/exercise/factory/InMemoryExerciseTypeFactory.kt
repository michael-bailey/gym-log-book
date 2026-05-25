@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.factory

import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel
import org.koin.core.annotation.Singleton
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Singleton(binds = [IExerciseTypeFactory::class])
class InMemoryExerciseTypeFactory : IExerciseTypeFactory {
	override fun create(newExerciseType: NewExerciseTypeModel): ExerciseTypeModel = ExerciseTypeModel(
		id = Uuid.random(),
		name = newExerciseType.name,
		equipmentClass = newExerciseType.equipmentClass,
	)
}
