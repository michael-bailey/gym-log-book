@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.factory

import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import org.koin.core.annotation.Singleton
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Singleton(binds = [IExerciseTypeFactory::class])
class InMemoryExerciseTypeFactory : IExerciseTypeFactory {
	override fun create(
		name: String,
		equipmentClass: EquipmentClass,
	): ExerciseType = ExerciseType(
		id = Uuid.random(),
		name = name,
		equipmentClass = equipmentClass,
		isUsingUserWeight = equipmentClass == EquipmentClass.UsesUserWeight,
	)
}