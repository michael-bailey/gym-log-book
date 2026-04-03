package net.michael_bailey.gym_log_book.server.exercise.service

import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType

interface IMutableExerciseTypeService : IExerciseTypeService {

	suspend fun createNewExerciseType(name: String, equipmentClass: EquipmentClass): ExerciseType
}