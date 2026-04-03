package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.Deferred
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType

interface IMutableExerciseTypeRepository : IExerciseTypeRepository {
	fun createNewType(name: String, equipmentClass: EquipmentClass): Deferred<ExerciseType>
}