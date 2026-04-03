package net.michael_bailey.gym_log_book.server.exercise.factory

import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType

interface IExerciseTypeFactory {
	fun create(name: String, equipmentClass: EquipmentClass): ExerciseType

}
