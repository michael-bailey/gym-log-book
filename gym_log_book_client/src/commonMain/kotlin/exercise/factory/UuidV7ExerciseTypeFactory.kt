@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.factory

import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UuidV7ExerciseTypeFactory(
	private val clock: Clock
) : IExerciseTypeEntityFactory {
	override fun create(
		name: String, equipmentClass: EquipmentClass
	): ExerciseTypeEntity = ExerciseTypeEntity(
		id = Uuid.generateV7(),
		date = clock.now(),
		name = name,
		usesUserWeight = false,
		equipmentClass = equipmentClass,
	)
}